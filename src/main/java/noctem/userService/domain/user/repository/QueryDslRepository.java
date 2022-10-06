package noctem.userService.domain.user.repository;

public interface QueryDslRepository {

    Integer cartTotalQty(Long userAccountId);
}
