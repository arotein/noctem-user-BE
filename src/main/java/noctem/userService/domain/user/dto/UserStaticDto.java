package noctem.userService.domain.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

public class UserStaticDto {

    @Data
    @NoArgsConstructor
    public static class PersonalOptionReqDto {
        private Long optionId;
        private String amount;
    }
}
