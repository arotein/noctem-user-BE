package noctem.userService.global.security.dto;

import lombok.*;
import noctem.userService.global.enumeration.Role;

@Getter
@Setter
@Builder
public class ClientInfoDto {
    private Long userAccountId;
    private String nickname;
    private String email;
    private Long storeAccountId;
    private Long storeId;
    private Role role;
}
