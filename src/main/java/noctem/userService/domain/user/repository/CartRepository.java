package noctem.userService.domain.user.repository;

import noctem.userService.domain.user.entity.Cart;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long>, QueryDslRepository {
    @EntityGraph(attributePaths = {"myPersonalOptionList", "userAccount"})
    List<Cart> findAllBySizeId(Long sizeId);

    @EntityGraph(attributePaths = {"myPersonalOptionList"})
    List<Cart> findAllByUserAccountId(Long userAccountId);

    @Override
    @EntityGraph(attributePaths = {"myPersonalOptionList", "userAccount"})
    Cart getById(Long cartId);
}
