package noctem.userService.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import noctem.userService.global.common.BaseEntity;

import javax.persistence.*;

@Entity
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyPersonalOption extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "my_personal_option_id")
    private Long id;
    private Long personalOptionId;
    private Integer amount;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "my_menu_id")
    private MyMenu myMenu;

    @Builder
    public MyPersonalOption(Long personalOptionId, Integer amount) {
        this.personalOptionId = personalOptionId;
        this.amount = amount;
    }

    public MyPersonalOption linkToMyMenu(MyMenu myMenu) {
        this.myMenu = myMenu;
        return this;
    }

    public MyPersonalOption linkToCart(Cart cart) {
        this.cart = cart;
        return this;
    }
}
