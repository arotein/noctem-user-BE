package noctem.userService.domain.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import noctem.userService.domain.user.entity.OptionalInfo;
import noctem.userService.domain.user.entity.UserAccount;
import noctem.userService.domain.user.entity.UserPrivacy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.sql.Timestamp;

import static noctem.userService.domain.user.entity.QOptionalInfo.optionalInfo;
import static noctem.userService.domain.user.entity.QUserAccount.userAccount;
import static noctem.userService.domain.user.entity.QUserPrivacy.userPrivacy;

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
    public UserAccount findUserAccount(Long id) {
        return queryFactory.selectFrom(userAccount)
                .where(userAccount.id.eq(id))
                .fetchOne();
    }

    @Override
    public UserAccount findUserAccountByEmail(String email) {
        return queryFactory.selectFrom(userAccount)
                .where(userAccount.email.eq(email))
                .fetchOne();
    }

    @Override
    public UserPrivacy findUserPrivacyByUserAccountId(Long id) {
        return queryFactory.selectFrom(userPrivacy)
                .join(userPrivacy.userAccount, userAccount).fetchJoin()
                .where(userAccount.id.eq(id))
                .fetchOne();
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

    @Override
    public OptionalInfo findOptionalInfoByUserAccountId(Long userAccountId) {
        return queryFactory.selectFrom(optionalInfo)
                .join(optionalInfo.userAccount, userAccount)
                .where(userAccount.id.eq(userAccountId))
                .fetchOne();
    }

    @Override
    public Boolean isDarkmode(Long userAccountId) {
        return queryFactory.select(optionalInfo.isDarkmode)
                .from(optionalInfo)
                .join(optionalInfo.userAccount, userAccount)
                .where(userAccount.id.eq(userAccountId))
                .fetchOne();
    }

    @Override
    public void updateLastAccessTime(Long id) {
        queryFactory.update(userAccount)
                .set(userAccount.lastAccessTime, new Timestamp(System.currentTimeMillis()))
                .where(userAccount.id.eq(id))
                .execute();
    }
}
