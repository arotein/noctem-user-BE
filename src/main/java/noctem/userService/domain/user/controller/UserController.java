package noctem.userService.domain.user.controller;

import lombok.RequiredArgsConstructor;
import noctem.userService.domain.user.dto.request.SignUpReqDto;
import noctem.userService.domain.user.service.UserService;
import noctem.userService.global.common.CommonResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-service")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signUp")
    public CommonResponse signUp(@Validated @RequestBody SignUpReqDto dto) {
        return CommonResponse.builder()
                .data(userService.signUp(dto))
                .build();
    }

    @GetMapping("/duplicationCheck/email/{email}")
    public CommonResponse duplCheckEmail(@PathVariable String email) {
        return CommonResponse.builder()
                .data(userService.duplCheckEmail(email))
                .build();
    }

    @GetMapping("/duplicationCheck/nickname/{nickname}")
    public CommonResponse duplCheckNickname(@PathVariable String nickname) {
        return CommonResponse.builder()
                .data(userService.duplCheckNickname(nickname))
                .build();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/optionalInfo/darkmode")
    public CommonResponse isDarkmode() {
        return CommonResponse.builder()
                .data(userService.isDarkmode())
                .build();
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/optionalInfo/darkmode")
    public CommonResponse changeDarkmode() {
        return CommonResponse.builder()
                .data(userService.changeDarkmode())
                .build();
    }
}
