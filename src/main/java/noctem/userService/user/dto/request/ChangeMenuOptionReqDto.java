package noctem.userService.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChangeMenuOptionReqDto {
    private Long myPersonalOptionId;
    private String amount;
}
