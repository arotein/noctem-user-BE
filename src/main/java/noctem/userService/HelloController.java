package noctem.userService;

import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RefreshScope
@RequestMapping("/api/user-service")
public class HelloController {
    private final List<String> list = new ArrayList<>();
    @Value("${global.value}")
    private String value;

    @GetMapping("/yml")
    public String yml() {
        return value;
    }

    @GetMapping("/hello")
    @Timed(value = "users.hello", longTask = true)
    public String hello() {
        int count = 0;
        log.info("안농 ㅋㅋ");
        try {

            while (count <= 100000) {
                list.add("안녕 ㅎㅎ");
                if (count % 100 == 0) {
                    log.info("안농 ㅋㅋ");
                }
                if (count % 10000 == 0) {
                    throw new IllegalStateException("응 에러 ㅋ");
                }
                count++;
            }
        } catch (Exception e) {
            log.error("응 에러 ㅋㅋ");
        }
        return "안농 ^_^";
    }

    @PostMapping("/hello")
    @Timed(value = "users.hello", longTask = true)
    public String helloPost() {
        log.info("안농 ㅋㅋ POST얌");
        return "안농 ^_^";
    }
}
