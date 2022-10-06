package noctem.userService.domain.user.feignClient;

import noctem.userService.domain.user.dto.request.MenuInfoResServDto;
import noctem.userService.global.common.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "${MENU_SERVICE}")
public interface MenuFeignClient {
//    @GetMapping("/api/${MENU_SERVICE}/menu/cart")
//    CommonResponse<List<MenuInfoResServDto>> getMenuInfoListDtoList(List<MenuInfoReqServDto> dtoList);

    @GetMapping("/api/${MENU_SERVICE}/menu/cart/{cartOrMyMenuId}/{sizeId}")
    CommonResponse<MenuInfoResServDto> getMenuInfoListDtoList(
            @PathVariable Long cartOrMyMenuId,
            @PathVariable Long sizeId,
            @RequestParam List<Long> optionIdList
    );
}
