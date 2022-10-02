package noctem.userService.domain.user.repository;

import noctem.userService.domain.user.entity.MyPersonalOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyPersonalOptionRepository extends JpaRepository<MyPersonalOption, Long> {
    List<MyPersonalOption> findAllByMyMenuId(Long id);
}
