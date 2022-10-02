package noctem.userService.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import noctem.userService.domain.user.dto.request.AddMenuReqDto;
import noctem.userService.domain.user.dto.request.ChangeMenuOptionReqDto;
import noctem.userService.domain.user.dto.request.ChangeMenuQtyReqDto;
import noctem.userService.domain.user.dto.response.CartAndOptionsResDto;
import noctem.userService.domain.user.entity.Cart;
import noctem.userService.domain.user.entity.MyPersonalOption;
import noctem.userService.domain.user.repository.CartRepository;
import noctem.userService.domain.user.repository.UserAccountRepository;
import noctem.userService.global.common.CommonException;
import noctem.userService.global.enumeration.Amount;
import noctem.userService.global.security.bean.ClientInfoLoader;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final UserAccountRepository userAccountRepository;
    private final CartRepository cartRepository;
    private final ClientInfoLoader clientInfoLoader;

    @Override
    public List<CartAndOptionsResDto> getCartList() {
        // menu-service에서 데이터 받아와서 리턴
        return null;
    }

    @Override
    public Boolean addMenuToCart(AddMenuReqDto dto) {
        // 커스널 옵션까지 모두 같다 -> 수량 증가
        // 커스널 옵션까지 모두 같지 않다 -> 새 객체 생성
        return null;
    }

    @Override
    public Boolean changeMenuQty(Long sizeId, ChangeMenuQtyReqDto dto) {
        cartRepository.findById(sizeId).get().changeQty(dto.getQty());
        return true;
    }

    @Override
    public Boolean changeMenuOption(Long sizeId, List<ChangeMenuOptionReqDto> dtoList) {
        Cart cart = identificationMenu(sizeId);
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
    public Boolean delMenu(Long sizeId) {
        identificationMenu(sizeId);
        cartRepository.deleteById(sizeId);
        return true;
    }

    // 요청한 menu가 본인 것이 맞는지 확인
    private Cart identificationMenu(Long sizeId) {
        Cart cart = cartRepository.findBySizeId(sizeId);
        if (cart.getUserAccount().getId() != clientInfoLoader.getUserAccountId()) {
            throw CommonException.builder().errorCode(2001).httpStatus(HttpStatus.UNAUTHORIZED).build();
        }
        return cart;
    }
}
