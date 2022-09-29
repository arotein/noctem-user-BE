package noctem.userService.domain.user.service;

import noctem.userService.domain.user.dto.request.ChangeNicknameReqDto;
import noctem.userService.domain.user.dto.request.SignUpReqDto;
import noctem.userService.domain.user.dto.response.UserPrivacyInfoResDto;

public interface UserService {
    Boolean signUp(SignUpReqDto dto);

    Boolean duplCheckEmail(String email);

    Boolean duplCheckNickname(String nickname);

    Boolean isDarkmode();

    Boolean changeDarkmode();

    UserPrivacyInfoResDto getPrivacyInfo();

    Boolean changeNickname(ChangeNicknameReqDto dto);

    void updateLastAccessTime(Long id);
}
