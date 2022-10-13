package noctem.userService.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import noctem.userService.global.enumeration.Sex;

@Data
@AllArgsConstructor
public class UserAccountInfoResDto {
    private Sex userSex;
    private Integer userAge;
}
