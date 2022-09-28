package noctem.userService.global.enumeration;

import noctem.userService.global.common.CommonException;
import org.springframework.http.HttpStatus;

public enum Sex {
    MALE("MALE"),
    FEMALE("FEMALE"),
    CONSTRUCT(null);

    private String value;

    Sex(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public Sex findInstance(String str) {
        switch (str.strip().toUpperCase()) {
            case "M":
            case "MALE":
                return Sex.MALE;
            case "F":
            case "FEMALE":
                return Sex.FEMALE;
            default:
                throw CommonException.builder().errorCode(2010).httpStatus(HttpStatus.BAD_REQUEST).build();
        }
    }
}
