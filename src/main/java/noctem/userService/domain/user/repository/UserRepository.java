package noctem.userService.domain.user.repository;

import noctem.userService.domain.user.entity.UserAccount;

public interface UserRepository {
    Boolean saveUserAccount(UserAccount userAccount);

    Boolean existEmail(String email);

    Boolean existNickname(String nickname);
}
