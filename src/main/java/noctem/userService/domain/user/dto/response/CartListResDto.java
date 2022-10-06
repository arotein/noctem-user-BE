package noctem.userService.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CartListResDto {
    private Integer index;
    private String menuName;
    private String menuEngName;
    private String menuImg;
    private Integer totalPrice; // 현재 퍼스널 옵션을 제외한 가격임
    private Integer qty;
    private List<String> myPersonalOptionList;
}
