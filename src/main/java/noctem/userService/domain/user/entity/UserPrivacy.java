package noctem.userService.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import noctem.userService.global.common.BaseEntity;
import noctem.userService.global.enumeration.MobileCarrier;
import noctem.userService.global.enumeration.Sex;

import javax.persistence.*;

/***
 * mobileCarrier(통신사) : SKT|KT|LGU+
 * phoneNumber : 010\\d{7, 8} (프론트에서 하이픈 지우고 보내기)
 * birthdayYear : 00 ~ 99
 * birthdayMonth : 1 ~ 12
 * birthdayDay : 1 ~ 31 (상세검증은 프론트에서)
 * sex : M|F
 */
@Entity
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserPrivacy extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_privacy_id")
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private MobileCarrier mobileCarrier;
    private String phoneNumber;
    private String birthdayYear;
    private String birthdayMonth;
    private String birthdayDay;
    @Enumerated(EnumType.STRING)
    private Sex sex;

    @JsonIgnore
    @OneToOne(mappedBy = "userPrivacy", fetch = FetchType.LAZY)
    private UserAccount userAccount;

    @Builder
    public UserPrivacy(String name, MobileCarrier mobileCarrier, String phoneNumber, String birthdayYear, String birthdayMonth, String birthdayDay, Sex sex) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birthdayYear = birthdayYear;
        this.birthdayMonth = birthdayMonth;
        this.birthdayDay = birthdayDay;
        this.sex = sex;
    }

    public UserPrivacy linkToUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
        return this;
    }
}
