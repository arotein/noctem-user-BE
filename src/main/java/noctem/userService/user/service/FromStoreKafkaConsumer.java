package noctem.userService.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import noctem.userService.AppConfig;
import noctem.userService.global.enumeration.Grade;
import noctem.userService.user.domain.entity.UserAccount;
import noctem.userService.user.domain.repository.UserAccountRepository;
import noctem.userService.user.dto.request.IncreaseUserExpKafkaDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
@RequiredArgsConstructor
public class FromStoreKafkaConsumer {
    private final String STORE_TO_USER_GRADE_EXP_TOPIC = "store-to-user-grade-exp";
    private final UserAccountRepository userAccountRepository;

    @KafkaListener(topics = STORE_TO_USER_GRADE_EXP_TOPIC)
    public void increaseUserExp(String userExpDtoAsJson) {
        log.info("Receive userExpDto through [{}] TOPIC", STORE_TO_USER_GRADE_EXP_TOPIC);
        try {
            IncreaseUserExpKafkaDto userExpDto = AppConfig.objectMapper()
                    .readValue(userExpDtoAsJson, IncreaseUserExpKafkaDto.class);

            UserAccount userAccount = userAccountRepository.getById(userExpDto.getUserAccountId());
            Grade beforeGrade = userAccount.getGrade();
            Grade nowGrade = userAccount.increaseAndGetGradeExp(userExpDto.getPurchaseTotalPrice());
            if (beforeGrade != nowGrade) {
                // 등급업 푸시알림
            }
        } catch (JsonMappingException e) {
            log.warn("Occurred JsonMappingException. [{}]", FromStoreKafkaConsumer.class.getSimpleName());
        } catch (JsonProcessingException e) {
            log.warn("Occurred JsonProcessingException. [{}]", FromStoreKafkaConsumer.class.getSimpleName());
        }

    }
}
