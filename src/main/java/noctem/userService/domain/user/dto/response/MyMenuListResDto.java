package noctem.userService.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MyMenuListResDto {
    private Integer index;
    private String alias;
    private String menuName;
    private String menuImg;
    private String temperature;
    private String size;
    private Integer totalPrice; // 현재 퍼스널 옵션을 제외한 가격임
    private List<String> myPersonalOptionList;

    public MyMenuListResDto changeTempAndSizeFormat() {
        temperature = temperature.toUpperCase();
        size = size.substring(0, 1).toUpperCase() + size.substring(1).toLowerCase();
        return this;
    }
}
