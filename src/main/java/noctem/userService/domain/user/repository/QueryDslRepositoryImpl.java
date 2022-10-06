package noctem.userService.domain.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import static noctem.userService.domain.user.entity.QCart.cart;
import static noctem.userService.domain.user.entity.QUserAccount.userAccount;

@Repository
public class QueryDslRepositoryImpl implements QueryDslRepository {
    private final EntityManager entityManager;
    private final JPAQueryFactory queryFactory;

    @Autowired
    public QueryDslRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Integer cartTotalQty(Long userAccountId) {
        return queryFactory.select(cart.qty.sum())
                .from(cart)
                .join(cart.userAccount, userAccount)
                .where(userAccount.id.eq(userAccountId))
                .fetchOne();
    }
}
