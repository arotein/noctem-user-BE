package noctem.userService.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import noctem.userService.user.dto.MenuComparisonJsonDto;
import noctem.userService.user.dto.request.AddCartReqDto;
import noctem.userService.user.dto.request.ChangeMenuOptionReqDto;
import noctem.userService.user.dto.request.ChangeMenuQtyReqDto;
import noctem.userService.user.dto.request.MenuInfoResServDto;
import noctem.userService.user.dto.response.CartListResDto;
import noctem.userService.user.domain.entity.Cart;
import noctem.userService.user.domain.entity.MyPersonalOption;
import noctem.userService.user.domain.feignClient.MenuFeignClient;
import noctem.userService.user.domain.repository.CartRepository;
import noctem.userService.user.domain.repository.MyPersonalOptionRepository;
import noctem.userService.user.domain.repository.UserAccountRepository;
import noctem.userService.global.common.CommonException;
import noctem.userService.global.enumeration.Amount;
import noctem.userService.global.security.bean.ClientInfoLoader;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final UserAccountRepository userAccountRepository;
    private final MyPersonalOptionRepository myPersonalOptionRepository;
    private final CartRepository cartRepository;
    private final ClientInfoLoader clientInfoLoader;
    private final MenuFeignClient menuFeignClient;

    @Transactional(readOnly = true)
    @Override
    public List<CartListResDto> getCartList() {
        List<Cart> cartList = cartRepository.findAllByUserAccountId(clientInfoLoader.getUserAccountId());
        Map<Long, Integer> cartMap = cartList.stream().collect(Collectors.toMap(Cart::getId, Cart::getQty));

        List<MenuInfoResServDto> menuInfoList = cartList.stream().map(e -> menuFeignClient.getMenuInfoListDtoList(
                e.getId(), e.getSizeId(), e.getMyPersonalOptionList().stream().map(MyPersonalOption::getId).collect(Collectors.toList())
        ).getData()).collect(Collectors.toList());

        return menuInfoList.stream().map(e -> new CartListResDto(
                null, e.getSizeId(), e.getMenuName(), e.getMenuEngName(), e.getMenuImg(),
                e.getTemperature(), e.getSize(), e.getTotalPrice(),
                cartMap.get(e.getCartOrMyMenuId()), new ArrayList<>()
        ).changeTempAndSizeFormat()).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public Integer getCartTotalQty() {
        return cartRepository.cartTotalQty(clientInfoLoader.getUserAccountId());
    }

    @Override
    public Boolean addMenuToCart(AddCartReqDto dto) {
        List<Cart> cartList = cartRepository.findAllByUserAccountId(clientInfoLoader.getUserAccountId());
        Map<String, Cart> cartMap = new HashMap<>();
        cartList.forEach(e -> cartMap.put(new MenuComparisonJsonDto().cartAndOptionEntityToJson(e), e));
        String dtoJson = new MenuComparisonJsonDto().addCartReqDtoToJson(dto);
        if (cartMap.containsKey(dtoJson)) {
            // 이미 존재하는 메뉴 -> 수량만 추가
            cartMap.get(dtoJson).plusQty(dto.getQuantity());
        } else {
            // 존재하지 않는 메뉴 -> 추가
            Cart cart = Cart.builder().sizeId(dto.getSizeId()).qty(dto.getQuantity()).build();
            cart.linkToUserAccount(userAccountRepository.getById(clientInfoLoader.getUserAccountId()));
            dto.getPersonalOptionList().forEach(e ->
                    cart.linkToMyPersonalOption(MyPersonalOption.builder()
                            .personalOptionId(e.getOptionId())
                            .amount(Amount.findByValue(e.getAmount()))
                            .build())
            );
            cartRepository.save(cart);
        }
        return true;
    }

    @Override
    public Boolean changeMenuQty(Long cartId, ChangeMenuQtyReqDto dto) {
        identificationCart(cartId).changeQty(dto.getQty());
        return true;
    }

    @Override
    public Boolean changeMenuOption(Long cartId, List<ChangeMenuOptionReqDto> dtoList) {
        Cart cart = identificationCart(cartId);
        myPersonalOptionRepository.deleteAll(cart.getMyPersonalOptionList());
        dtoList.forEach(e ->
                cart.linkToMyPersonalOption(
                        MyPersonalOption.builder()
                                .personalOptionId(e.getMyPersonalOptionId())
                                .amount(Amount.findByValue(e.getAmount()))
                                .build()
                )
        );
        return true;
    }

    @Override
    public Boolean delMenu(Long cartId) {
        cartRepository.delete(identificationCart(cartId));
        return true;
    }

    // 요청한 cart가 본인 것이 맞는지 확인
    private Cart identificationCart(Long cartId) {
        Optional<Cart> optionalCart = cartRepository.findById(cartId);
        if (optionalCart.isEmpty()) {
            throw CommonException.builder().errorCode(2024).httpStatus(HttpStatus.BAD_REQUEST).build();
        }
        if (!Objects.equals(optionalCart.get().getUserAccount().getId(), clientInfoLoader.getUserAccountId())) {
            throw CommonException.builder().errorCode(2001).httpStatus(HttpStatus.UNAUTHORIZED).build();
        }
        return optionalCart.get();
    }
}
