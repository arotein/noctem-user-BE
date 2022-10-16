package noctem.userService.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IncreaseUserExpKafkaDto {
    private Long userAccountId;
    private Integer purchaseTotalPrice;
}
