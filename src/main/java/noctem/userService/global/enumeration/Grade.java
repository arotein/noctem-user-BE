package noctem.userService.global.enumeration;

import noctem.userService.global.common.CommonException;
import org.springframework.http.HttpStatus;

public enum Grade {
    TALL("Tall"),
    GRANDE("Grande"),
    VENTI("Venti"),
    CONSTRUCT(null);

    private String value;

    Grade(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public Grade findInstance(String str) {
        switch (str.strip().toUpperCase()) {
            case "TALL":
                return Grade.TALL;
            case "GRANDE":
                return Grade.GRANDE;
            case "VENTI":
                return Grade.VENTI;
            default:
                throw CommonException.builder().errorCode(2005).httpStatus(HttpStatus.BAD_REQUEST).build();
        }
    }
}
