package noctem.userService.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncreaseUserExpKafkaDto {
    @NotBlank
    private Long userAccountId;
    @NotBlank
    private Integer purchaseTotalPrice;
}
