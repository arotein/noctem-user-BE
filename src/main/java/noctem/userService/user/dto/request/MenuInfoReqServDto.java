package noctem.userService.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuInfoReqServDto {
    @NotBlank
    private Long cartOrMyMenuId;
    @NotBlank
    private Long sizeId;
    private List<Long> optionIdList;
}
