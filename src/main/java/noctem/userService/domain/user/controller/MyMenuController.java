package noctem.userService.domain.user.controller;

import lombok.RequiredArgsConstructor;
import noctem.userService.domain.user.dto.request.AddMyMenuReqDto;
import noctem.userService.domain.user.dto.request.ChangeMyMenuAliasReqDto;
import noctem.userService.domain.user.dto.request.ChangeMyMenuOrderReqDto;
import noctem.userService.domain.user.service.MyMenuService;
import noctem.userService.global.common.CommonResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${global.api.base-path}/myMenu")
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
public class MyMenuController {
    private final MyMenuService myMenuService;

    @GetMapping("")
    public CommonResponse getMyMenuList() {
        return CommonResponse.builder()
                .data(myMenuService.getMyMenuList())
                .build();
    }

    @PostMapping("")
    public CommonResponse addMyMenu(@Validated @RequestBody AddMyMenuReqDto dto) {
        return CommonResponse.builder()
                .data(myMenuService.addMyMenu(dto))
                .build();
    }

    @PatchMapping("/order")
    public CommonResponse changeMyMenuOrder(@RequestBody ChangeMyMenuOrderReqDto dto) {
        return CommonResponse.builder()
                .data(myMenuService.changeMyMenuOrder(dto))
                .build();
    }

    @PatchMapping("/{myMenuId}/alias")
    public CommonResponse changeMyMenuAlias(@PathVariable Long myMenuId,
                                            @Validated @RequestBody ChangeMyMenuAliasReqDto dto) {
        return CommonResponse.builder()
                .data(myMenuService.changeMyMenuAlias(myMenuId, dto))
                .build();
    }

    @DeleteMapping("/{myMenuId}")
    public CommonResponse delMyMenu(@PathVariable Long myMenuId) {
        return CommonResponse.builder()
                .data(myMenuService.delMyMenu(myMenuId))
                .build();
    }
}
