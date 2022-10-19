package noctem.userService.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import noctem.userService.user.dto.UserStaticDto;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@NoArgsConstructor
public class AddCartReqDto {
    @NotBlank
    private Long sizeId;
    @NotBlank
    private Integer quantity;
    private List<UserStaticDto.PersonalOptionReqDto> personalOptionList;
}
