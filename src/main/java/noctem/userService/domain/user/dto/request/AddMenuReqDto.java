package noctem.userService.domain.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import noctem.userService.domain.user.dto.UserStaticDto;

import java.util.List;

@Getter
@NoArgsConstructor
public class AddMenuReqDto {
    private Long sizeId;
    private Integer quantity;
    private List<UserStaticDto.PersonalOptionReqDto> personalOptionList;
}
