package noctem.userService.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartListResDto {
    private Integer totalMenuPrice;
    private Integer qty;
}
