package noctem.userService.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuInfoReqServDto {
    private Long cartOrMyMenuId;
    private Long sizeId;
    private List<Long> optionIdList;
}
