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
@PreAuthorize("hasRole('USER')")
public class OptionalInfoController {
    private final UserService userService;

    @GetMapping("")
    public CommonResponse getOptionalInfo() {
        return CommonResponse.builder()
                .data(userService.getAllOptionalInfo())
                .build();
    }

    @GetMapping("/darkmode")
    public CommonResponse isDarkmode() {
        return CommonResponse.builder()
                .data(userService.isDarkmode())
                .build();
    }

    @PutMapping("/darkmode")
    public CommonResponse changeDarkmode() {
        return CommonResponse.builder()
                .data(userService.changeDarkmode())
                .build();
    }

    @PutMapping("/pushNotification")
    public CommonResponse changePushNotificationAgreement() {
        return CommonResponse.builder()
                .data(userService.changePushNotificationAgreement())
                .build();
    }

    @PutMapping("/advertisement")
    public CommonResponse changeAdvertisementAgreement() {
        return CommonResponse.builder()
                .data(userService.changeAdvertisementAgreement())
                .build();
    }

    @PutMapping("/location")
    public CommonResponse changeUseLocationInfoAgreement() {
        return CommonResponse.builder()
                .data(userService.changeUseLocationInfoAgreement())
                .build();
    }

    @PutMapping("/shakeToPay")
    public CommonResponse changeShakeToPay() {
        return CommonResponse.builder()
                .data(userService.changeShakeToPay())
                .build();
    }
}
