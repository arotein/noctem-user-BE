package noctem.userService.domain.user.repository;

import noctem.userService.domain.user.entity.OptionalInfo;
import noctem.userService.domain.user.entity.UserAccount;
import noctem.userService.domain.user.entity.UserPrivacy;

public interface UserRepository {
    Boolean saveUserAccount(UserAccount userAccount);

    UserAccount findUserAccount(Long userAccountId);

    UserAccount findUserAccountByEmail(String email);

    UserPrivacy findUserPrivacyByUserAccountId(Long userAccountId);

    Boolean existEmail(String email);

    Boolean existNickname(String nickname);

    OptionalInfo findOptionalInfoByUserAccountId(Long userAccountId);

    Boolean isDarkmode(Long userAccountId);

    void updateLastAccessTime(Long id);
}
