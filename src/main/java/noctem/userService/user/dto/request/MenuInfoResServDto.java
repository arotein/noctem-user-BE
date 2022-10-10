package noctem.userService.user.dto.request;

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
    private String temperature;
    private String size;
    private Integer totalPrice; // 옵션을 제외한 메뉴가격
}
