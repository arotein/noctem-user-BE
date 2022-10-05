package noctem.userService.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import noctem.userService.domain.user.dto.MenuComparisonJsonDto;
import noctem.userService.domain.user.dto.request.AddCartReqDto;
import noctem.userService.domain.user.dto.request.CartAndOptionsReqServDto;
import noctem.userService.domain.user.dto.request.ChangeMenuOptionReqDto;
import noctem.userService.domain.user.dto.request.ChangeMenuQtyReqDto;
import noctem.userService.domain.user.dto.response.CartListResDto;
import noctem.userService.domain.user.entity.Cart;
import noctem.userService.domain.user.entity.MyPersonalOption;
import noctem.userService.domain.user.feignClient.MenuFeignClient;
import noctem.userService.domain.user.repository.CartRepository;
import noctem.userService.domain.user.repository.UserAccountRepository;
import noctem.userService.global.common.CommonException;
import noctem.userService.global.enumeration.Amount;
import noctem.userService.global.security.bean.ClientInfoLoader;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final UserAccountRepository userAccountRepository;
    private final CartRepository cartRepository;
    private final ClientInfoLoader clientInfoLoader;
    private final MenuFeignClient menuFeignClient;

    @Override
    public List<CartListResDto> getCartList() {
        List<Cart> cartList = cartRepository.findAllByUserAccountId(clientInfoLoader.getUserAccountId());
        List<CartListResDto> data = menuFeignClient.getCartListDtoList(cartList.stream()
                        .map(e -> new CartAndOptionsReqServDto(e.getSizeId(),
                                e.getMyPersonalOptionList().stream()
                                        .map(MyPersonalOption::getPersonalOptionId)
                                        .collect(Collectors.toList())))
                        .collect(Collectors.toList()))
                .getData();
        // data에 qty와 totalMenuPrice 추가하여 리턴
        return null;
    }

    @Override
    public Long getCartTotalQty() {
        return cartRepository.countByUserAccountId(clientInfoLoader.getUserAccountId());
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
        Map<Long, MyPersonalOption> optionMap = cart.getMyPersonalOptionList()
                .stream().collect(Collectors.toMap(MyPersonalOption::getId, e -> e));
        dtoList.forEach(e -> {
            if (optionMap.containsKey(e.getMyPersonalOptionId())) {
                optionMap.get(e.getMyPersonalOptionId())
                        .changeAmount(Amount.findByValue(e.getAmount()));
            } else {
                cart.linkToMyPersonalOption(MyPersonalOption.builder()
                        .personalOptionId(e.getMyPersonalOptionId())
                        .amount(Amount.findByValue(e.getAmount()))
                        .build());
            }
        });
        return true;
    }

    @Override
    public Boolean delMenu(Long cartId) {
        cartRepository.delete(identificationCart(cartId));
        return true;
    }

    // 요청한 cart가 본인 것이 맞는지 확인
    private Cart identificationCart(Long cartId) {
        Cart cart = cartRepository.getById(cartId);
        if (!Objects.equals(cart.getUserAccount().getId(), clientInfoLoader.getUserAccountId())) {
            throw CommonException.builder().errorCode(2001).httpStatus(HttpStatus.UNAUTHORIZED).build();
        }
        return cart;
    }
}
