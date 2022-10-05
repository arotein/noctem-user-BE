package noctem.userService.global.common;

import com.google.common.io.CharStreams;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import noctem.userService.AppConfig;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

@Slf4j
@Component
public class FeignErrorDecoder implements ErrorDecoder {
    @Override
    public CommonException decode(String methodKey, Response response) {
        try {
            CommonRequest commonRequest = AppConfig.objectMapper().readValue(
                    CharStreams.toString(response.body().asReader(Charset.defaultCharset())),
                    CommonRequest.class);
            return CommonException.builder()
                    .errorCode(commonRequest.getErrorCode())
                    .httpStatus(HttpStatus.valueOf(response.status()))
                    .build();
        } catch (Exception e) {
            return CommonException.builder()
                    .errorCode(2021)
                    .httpStatus(HttpStatus.valueOf(response.status()))
                    .build();
        }
    }
}
