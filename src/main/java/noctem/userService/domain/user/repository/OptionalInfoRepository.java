package noctem.userService.domain.user.repository;

import noctem.userService.domain.user.entity.OptionalInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionalInfoRepository extends JpaRepository<OptionalInfo, Long> {
    OptionalInfo findByUserAccountId(Long id);
}
