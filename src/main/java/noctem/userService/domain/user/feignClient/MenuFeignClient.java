package noctem.userService.domain.user.feignClient;

import noctem.userService.domain.user.dto.request.CartAndOptionsReqServDto;
import noctem.userService.domain.user.dto.request.MyMenuAndOptionsReqServDto;
import noctem.userService.domain.user.dto.response.CartListResDto;
import noctem.userService.domain.user.dto.response.MyMenuListResDto;
import noctem.userService.global.common.CommonRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "${MENU_SERVICE}")
public interface MenuFeignClient {
    @GetMapping("/api/${MENU_SERVICE}/")
    CommonRequest<List<CartListResDto>> getCartListDtoList(List<CartAndOptionsReqServDto> dtoList);

    @GetMapping("/api/${MENU_SERVICE}/")
    CommonRequest<List<MyMenuListResDto>> getMyMenuListDtoList(List<MyMenuAndOptionsReqServDto> dtoList);
}
