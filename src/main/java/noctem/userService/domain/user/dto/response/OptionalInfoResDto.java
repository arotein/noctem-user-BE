package noctem.userService.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OptionalInfoResDto {
    private Boolean isDarkmode;
    private Boolean pushNotificationAgreement;
    private Boolean advertisementAgreement;
    private Boolean useLocationInfoAgreement;
    private Boolean shakeToPay;
}
