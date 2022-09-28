package noctem.userService.domain.user.service;

import noctem.userService.domain.user.dto.request.SignUpReqDto;

public interface UserService {
    Boolean signUp(SignUpReqDto dto);

    Boolean duplCheckEmail(String email);

    Boolean duplCheckNickname(String nickname);
}
