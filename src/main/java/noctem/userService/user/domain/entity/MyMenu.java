package noctem.userService.user.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import noctem.userService.global.common.BaseEntity;
import noctem.userService.global.enumeration.CupType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/***
 * alias : 계정당 유일해야됨
 * sequence : 나만의 메뉴 순서
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyMenu extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "my_menu_id")
    private Long id;
    private String alias;
    private Long sizeId;
    @Enumerated(EnumType.STRING)
    private CupType cupType;
    private Integer myMenuOrder;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_account_id")
    private UserAccount userAccount;

    @JsonIgnore
    @OneToMany(mappedBy = "myMenu", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MyPersonalOption> myPersonalOptionList = new ArrayList<>();

    @Builder
    public MyMenu(Long sizeId, String alias, CupType cupType) {
        this.sizeId = sizeId;
        this.alias = alias;
        this.cupType = cupType;
        this.myMenuOrder = 0;
    }

    public MyMenu linkToUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
        userAccount.linkToMyMenu(this);
        return this;
    }

    public MyMenu linkToMyPersonalOption(MyPersonalOption myPersonalOption) {
        this.myPersonalOptionList.add(myPersonalOption);
        myPersonalOption.linkToMyMenu(this);
        return this;
    }

    public MyMenu linkToMyPersonalOptionList(List<MyPersonalOption> myPersonalOptionList) {
        this.myPersonalOptionList.addAll(myPersonalOptionList);
        myPersonalOptionList.forEach(e -> e.linkToMyMenu(this));
        return this;
    }

    public MyMenu changeAlias(String alias) {
        this.alias = alias;
        return this;
    }

    public MyMenu changeMyMenuOrder(Integer myMenuOrder) {
        this.myMenuOrder = myMenuOrder;
        return this;
    }
}
