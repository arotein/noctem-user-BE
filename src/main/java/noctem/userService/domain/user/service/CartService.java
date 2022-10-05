package noctem.userService.domain.user.service;

import noctem.userService.domain.user.dto.request.AddMenuReqDto;
import noctem.userService.domain.user.dto.request.ChangeMenuOptionReqDto;
import noctem.userService.domain.user.dto.request.ChangeMenuQtyReqDto;
import noctem.userService.domain.user.dto.response.CartAndOptionsResDto;

import java.util.List;

public interface CartService {
    List<CartAndOptionsResDto> getCartList();

    Long getCartTotalQty();

    Boolean addMenuToCart(AddMenuReqDto dto);

    Boolean changeMenuQty(Long sizeId, ChangeMenuQtyReqDto dto);

    Boolean changeMenuOption(Long sizeId, List<ChangeMenuOptionReqDto> dtoList);

    Boolean delMenu(Long sizeId);
}
