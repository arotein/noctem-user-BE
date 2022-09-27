package noctem.userService.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import noctem.userService.global.common.BaseEntity;
import noctem.userService.global.common.CommonException;
import noctem.userService.global.enumeration.Grade;
import noctem.userService.global.enumeration.Role;
import org.springframework.http.HttpStatus;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(unique = true)
    private String loginId;
    private String password;
    private String name;
    private String nickname;
    private LocalDate dateOfBirth;
    private String email;
    private String phoneNumber;
    private Timestamp lastAccessTime;

    @Enumerated(EnumType.STRING)
    private Grade grade = Grade.TALL;
    private Integer gradeExp = 0;

    private Boolean isDarkmode = false;

    @Enumerated(EnumType.STRING)
    private Role role = Role.ROLE_USER;

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<MyMenu> myMenuList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Cart> cartList = new ArrayList<>();

    @Builder
    public User(String loginId, String password, String name, String nickname, LocalDate dateOfBirth, String email, String phoneNumber) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public void updateLastAccessTime() {
        this.lastAccessTime = new Timestamp(System.currentTimeMillis());
    }

    public String loginIdFormatMatching(String loginId) {
        if (loginId == null || !loginId.matches("^[\\da-zA-Z]{8,32}$")) {
            throw CommonException.builder().errorCode(2006).httpStatus(HttpStatus.BAD_REQUEST).build();
        }
        return loginId;
    }

    public String phoneNumberFormatMatching(String phoneNumber) {
        if (phoneNumber == null || !phoneNumber.matches("^010-\\d{3,4}-\\d{3,4}$")) {
            throw CommonException.builder().errorCode(2007).httpStatus(HttpStatus.BAD_REQUEST).build();
        }
        return phoneNumber;
    }

    public User linkToCart(Cart cart) {
        this.cartList.add(cart);
        return this;
    }

    public User linkToMyMenu(MyMenu myMenu) {
        this.myMenuList.add(myMenu);
        return this;
    }
}
