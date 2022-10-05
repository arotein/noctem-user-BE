package noctem.userService.domain.user.controller;

import lombok.RequiredArgsConstructor;
import noctem.userService.domain.user.dto.request.AddCartReqDto;
import noctem.userService.domain.user.dto.request.ChangeMenuOptionReqDto;
import noctem.userService.domain.user.dto.request.ChangeMenuQtyReqDto;
import noctem.userService.domain.user.service.CartService;
import noctem.userService.global.common.CommonResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${global.api.base-path}/carts")
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
public class CartController {
    private final CartService cartService;

    @GetMapping("")
    public CommonResponse getCartList() {
        return CommonResponse.builder()
                .data(cartService.getCartList())
                .build();
    }

    @GetMapping("/qty")
    public CommonResponse getCartTotalQty() {
        return CommonResponse.builder()
                .data(cartService.getCartTotalQty())
                .build();
    }

    @PostMapping("")
    public CommonResponse addMenuToCart(@Validated @RequestBody AddCartReqDto dto) {
        return CommonResponse.builder()
                .data(cartService.addMenuToCart(dto))
                .build();
    }

    @PatchMapping("/{sizeId}/quantities")
    public CommonResponse changeMenuQty(@PathVariable Long sizeId,
                                        @Validated @RequestBody ChangeMenuQtyReqDto dto) {
        return CommonResponse.builder()
                .data(cartService.changeMenuQty(sizeId, dto))
                .build();
    }

    @PatchMapping("/{sizeId}/personalOptions")
    public CommonResponse changeMenuOption(@PathVariable Long sizeId,
                                           @Validated @RequestBody List<ChangeMenuOptionReqDto> dtoList) {
        return CommonResponse.builder()
                .data(cartService.changeMenuOption(sizeId, dtoList))
                .build();
    }

    @DeleteMapping("/{sizeId}")
    public CommonResponse delMenu(@PathVariable Long sizeId) {
        return CommonResponse.builder()
                .data(cartService.delMenu(sizeId))
                .build();
    }
}
