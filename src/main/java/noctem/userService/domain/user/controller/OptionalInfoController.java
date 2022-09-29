package noctem.userService.domain.user.controller;

import lombok.RequiredArgsConstructor;
import noctem.userService.domain.user.service.UserService;
import noctem.userService.global.common.CommonResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user-service/optionalInfo")
@RequiredArgsConstructor
public class OptionalInfoController {
    private final UserService userService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/darkmode")
    public CommonResponse isDarkmode() {
        return CommonResponse.builder()
                .data(userService.isDarkmode())
                .build();
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/darkmode")
    public CommonResponse changeDarkmode() {
        return CommonResponse.builder()
                .data(userService.changeDarkmode())
                .build();
    }
}
