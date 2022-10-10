package noctem.userService.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CartListResDto {
    private Integer index;
    private Long sizeId;
    private String menuName;
    private String menuEngName;
    private String menuImg;
    private String temperature;
    private String size;
    private Integer totalMenuPrice; // 현재 퍼스널 옵션을 제외한 가격임
    private Integer qty;
    private List<String> myPersonalOptionList;

    public CartListResDto changeTempAndSizeFormat() {
        temperature = temperature.toUpperCase();
        size = size.substring(0, 1).toUpperCase() + size.substring(1).toLowerCase();
        return this;
    }
}
