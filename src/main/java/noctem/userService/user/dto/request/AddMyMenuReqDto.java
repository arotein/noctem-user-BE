package noctem.userService.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import noctem.userService.user.dto.UserStaticDto;

import java.util.List;

@Getter
@NoArgsConstructor
public class AddMyMenuReqDto {
    private Long sizeId;
    private String alias;
    private List<UserStaticDto.PersonalOptionReqDto> personalOptionList;
}