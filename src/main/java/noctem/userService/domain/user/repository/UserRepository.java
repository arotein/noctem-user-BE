package noctem.userService.domain.user.repository;

import noctem.userService.domain.user.entity.OptionalInfo;
import noctem.userService.domain.user.entity.UserAccount;

public interface UserRepository {
    Boolean saveUserAccount(UserAccount userAccount);

    UserAccount findUserAccount(Long id);

    UserAccount findUserAccountByEmail(String email);

    Boolean existEmail(String email);

    Boolean existNickname(String nickname);

    OptionalInfo findOptionalInfoByUserAccountId(Long userAccountId);

    Boolean isDarkmode(Long userAccountId);

    void updateLastAccessTime(Long id);
}
