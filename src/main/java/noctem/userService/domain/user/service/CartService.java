package noctem.userService.domain.user.service;

import noctem.userService.domain.user.dto.request.AddCartReqDto;
import noctem.userService.domain.user.dto.request.ChangeMenuOptionReqDto;
import noctem.userService.domain.user.dto.request.ChangeMenuQtyReqDto;
import noctem.userService.domain.user.dto.response.CartListResDto;

import java.util.List;

public interface CartService {
    List<CartListResDto> getCartList();

    Integer getCartTotalQty();

    Boolean addMenuToCart(AddCartReqDto dto);

    Boolean changeMenuQty(Long sizeId, ChangeMenuQtyReqDto dto);

    Boolean changeMenuOption(Long sizeId, List<ChangeMenuOptionReqDto> dtoList);

    Boolean delMenu(Long sizeId);
}
