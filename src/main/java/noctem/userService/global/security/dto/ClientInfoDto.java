package noctem.userService.global.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import noctem.userService.global.enumeration.Role;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientInfoDto {
    private Long userAccountId;
    private String nickname;
    private String email;
    private Long storeAccountId;
    private Long storeId;
    private Role role;
}
