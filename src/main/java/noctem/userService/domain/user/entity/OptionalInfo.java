package noctem.userService.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import noctem.userService.global.common.BaseEntity;

import javax.persistence.*;

/***
 * pushNotificationAgreement : 푸시 알림 동의 여부
 * advertisementAgreement : 광고 수신 동의 여부
 * useLocationInfoAgreement : 위치정보 이용 동의 여부
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OptionalInfo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "optional_info_id")
    private Long id;
    private Boolean isDarkmode;
    private Boolean pushNotificationAgreement;
    private Boolean advertisementAgreement;
    private Boolean useLocationInfoAgreement;

    @JsonIgnore
    @OneToOne(mappedBy = "optionalInfo", fetch = FetchType.LAZY)
    private UserAccount userAccount;

    @Builder
    public OptionalInfo(Boolean pushNotificationAgreement, Boolean advertisementAgreement, Boolean useLocationInfoAgreement) {
        this.isDarkmode = false;
        this.pushNotificationAgreement = pushNotificationAgreement;
        this.advertisementAgreement = advertisementAgreement;
        this.useLocationInfoAgreement = useLocationInfoAgreement;
    }

    public OptionalInfo linkToUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
        return this;
    }

    public OptionalInfo changeDarkmode() {
        this.isDarkmode = this.isDarkmode ? false : true;
        return this;
    }
}
