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

@Entity
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;
    private Long sizeId;
    private Integer amount;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_account_id")
    private UserAccount userAccount;

    @JsonIgnore
    @OneToMany(mappedBy = "cart", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MyPersonalOption> myPersonalOptionList = new ArrayList<>();

    @Builder
    public Cart(Long sizeId, Integer amount) {
        this.sizeId = sizeId;
        this.amount = amount;
    }

    public Cart linkToUser(UserAccount userAccount) {
        this.userAccount = userAccount;
        userAccount.linkToCart(this);
        return this;
    }

    public Cart linkToMyPersonalOption(List<MyPersonalOption> myPersonalOptionList) {
        this.myPersonalOptionList.addAll(myPersonalOptionList);
        myPersonalOptionList.forEach(e -> e.linkToCart(this));
        return this;
    }
}
