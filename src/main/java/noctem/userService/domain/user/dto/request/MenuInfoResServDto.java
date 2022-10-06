package noctem.userService.domain.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MenuInfoResServDto {
    private Long cartOrMyMenuId;
    private Long sizeId;
    private String menuName;
    private String menuEngName;
    private String menuImg;
    private Integer totalPrice;
}
