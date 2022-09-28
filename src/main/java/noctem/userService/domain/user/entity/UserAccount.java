package noctem.userService.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import noctem.userService.global.common.BaseEntity;
import noctem.userService.global.enumeration.Grade;
import noctem.userService.global.enumeration.Role;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/***
 * password : 8 ~ 40자리
 * nickname : 2 ~ 8자리
 */
@Entity
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserAccount extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_account_id")
    private Long id;
    @Column(unique = true)
    private String email;
    private String password;

    @Column(unique = true)
    private String nickname;

    private Timestamp lastAccessTime;

    @Enumerated(EnumType.STRING)
    private Grade grade = Grade.TALL;
    private Integer gradeExp = 0;

    @Enumerated(EnumType.STRING)
    private Role role = Role.ROLE_USER;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_privacy_id")
    private UserPrivacy userPrivacy;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "required_info_id")
    private RequiredInfo requiredInfo;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "optional_info_id")
    private OptionalInfo optionalInfo;

    @JsonIgnore
    @OneToMany(mappedBy = "userAccount", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<MyMenu> myMenuList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "userAccount", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Cart> cartList = new ArrayList<>();

    @Builder
    public UserAccount(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    public void updateLastAccessTime() {
        this.lastAccessTime = new Timestamp(System.currentTimeMillis());
    }

    public UserAccount linkToCart(Cart cart) {
        this.cartList.add(cart);
        return this;
    }

    public UserAccount linkToMyMenu(MyMenu myMenu) {
        this.myMenuList.add(myMenu);
        return this;
    }

    public UserAccount linkToUserPrivacy(UserPrivacy userPrivacy) {
        this.userPrivacy = userPrivacy;
        userPrivacy.linkToUserAccount(this);
        return this;
    }

    public UserAccount linkToRequiredInfo(RequiredInfo requiredInfo) {
        this.requiredInfo = requiredInfo;
        requiredInfo.linkToUserAccount(this);
        return this;
    }

    public UserAccount linkToOptionalInfo(OptionalInfo optionalInfo) {
        this.optionalInfo = optionalInfo;
        optionalInfo.linkToUserAccount(this);
        return this;
    }
}
