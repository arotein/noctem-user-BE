package noctem.userService.domain.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import noctem.userService.domain.user.entity.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import static noctem.userService.domain.user.entity.QUserAccount.userAccount;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final EntityManager entityManager;
    private final JPAQueryFactory queryFactory;

    @Autowired
    public UserRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Boolean saveUserAccount(UserAccount userAccount) {
        entityManager.persist(userAccount);
        return true;
    }

    @Override
    public Boolean existEmail(String email) {
        return null != queryFactory.selectFrom(userAccount)
                .where(userAccount.email.eq(email))
                .fetchOne();
    }

    @Override
    public Boolean existNickname(String nickname) {
        return null != queryFactory.selectOne()
                .from(userAccount)
                .where(userAccount.nickname.eq(nickname))
                .fetchOne();
    }
}
