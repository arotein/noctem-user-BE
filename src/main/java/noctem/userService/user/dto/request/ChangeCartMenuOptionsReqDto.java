package noctem.userService.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@NoArgsConstructor
public class ChangeCartMenuOptionsReqDto {
    @NotBlank
    private String cupType;
    private List<ChangeMenuPersonalOptionReqDto> personalOptionList;
}
