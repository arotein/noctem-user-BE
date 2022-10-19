package noctem.userService.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import noctem.userService.user.dto.UserStaticDto;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@NoArgsConstructor
public class AddMyMenuReqDto {
    @NotBlank
    private Long sizeId;
    @NotBlank
    private String alias;
    private List<UserStaticDto.PersonalOptionReqDto> personalOptionList;
}
