package noctem.userService.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import noctem.userService.global.common.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/***
 * alias는 계정당 유일해야됨
 */
@Entity
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyMenu extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "my_menu_id")
    private Long id;
    private Long sizeId;
    private String alias;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_account_id")
    private UserAccount userAccount;

    @JsonIgnore
    @OneToMany(mappedBy = "myMenu", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MyPersonalOption> myPersonalOptionList = new ArrayList<>();

    @Builder
    public MyMenu(Long sizeId, String alias) {
        this.sizeId = sizeId;
        this.alias = alias;
    }

    public MyMenu linkToUser(MyMenu myMenu) {
        this.userAccount = userAccount;
        userAccount.linkToMyMenu(this);
        return this;
    }

    public MyMenu linkToMyPersonalOption(List<MyPersonalOption> myPersonalOptionList) {
        this.myPersonalOptionList.addAll(myPersonalOptionList);
        myPersonalOptionList.forEach(e -> e.linkToMyMenu(this));
        return this;
    }
}
