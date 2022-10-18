package noctem.userService.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncreaseUserExpKafkaDto {
    private Long userAccountId;
    private Integer purchaseTotalPrice;
}
