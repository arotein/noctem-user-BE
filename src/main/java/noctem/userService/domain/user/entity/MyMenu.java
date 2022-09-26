package noctem.userService.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import noctem.userService.global.common.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "myMenu", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MyPersonalOption> myPersonalOptionList = new ArrayList<>();

    public MyMenu linkToUser(MyMenu myMenu) {
        this.user = user;
        user.linkToMyMenu(this);
        return this;
    }

    public MyMenu linkToMyPersonalOption(List<MyPersonalOption> myPersonalOptionList) {
        this.myPersonalOptionList.addAll(myPersonalOptionList);
        myPersonalOptionList.forEach(e -> e.linkToMyMenu(this));
        return this;
    }
}
