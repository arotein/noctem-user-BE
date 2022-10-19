package noctem.userService.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuInfoResServDto {
    @NotBlank
    private Long cartOrMyMenuId;
    @NotBlank
    private Long sizeId;
    @NotBlank
    private String menuName;
    @NotBlank
    private String menuEngName;
    @NotBlank
    private String menuImg;
    @NotBlank
    private String temperature;
    @NotBlank
    private String size;
    @NotBlank
    private Integer totalPrice; // 옵션을 제외한 메뉴가격
}
