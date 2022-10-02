package noctem.userService.domain.user.repository;

import noctem.userService.domain.user.entity.UserPrivacy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPrivacyRepository extends JpaRepository<UserPrivacy, Long> {

    UserPrivacy findByUserAccountId(Long id);
}
