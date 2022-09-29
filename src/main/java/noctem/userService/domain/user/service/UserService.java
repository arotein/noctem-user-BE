package noctem.userService.domain.user.service;

import noctem.userService.domain.user.dto.request.ChangeNicknameReqDto;
import noctem.userService.domain.user.dto.request.SignUpReqDto;
import noctem.userService.domain.user.dto.response.GradeAndRemainingExpResDto;
import noctem.userService.domain.user.dto.response.OptionalInfoResDto;
import noctem.userService.domain.user.dto.response.UserPrivacyInfoResDto;

public interface UserService {
    Boolean signUp(SignUpReqDto dto);

    Boolean duplCheckEmail(String email);

    Boolean duplCheckNickname(String nickname);

    OptionalInfoResDto getAllOptionalInfo();

    Boolean isDarkmode();

    Boolean changePushNotificationAgreement();

    Boolean changeAdvertisementAgreement();

    Boolean changeUseLocationInfoAgreement();

    Boolean changeShakeToPay();

    Boolean changeDarkmode();

    UserPrivacyInfoResDto getPrivacyInfo();

    Boolean changeNickname(ChangeNicknameReqDto dto);

    GradeAndRemainingExpResDto getGradeAndRemainingExp();

    void updateLastAccessTime(Long id);
}
