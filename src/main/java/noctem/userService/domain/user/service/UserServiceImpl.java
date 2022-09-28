package noctem.userService.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import noctem.userService.domain.user.dto.request.SignUpReqDto;
import noctem.userService.domain.user.entity.OptionalInfo;
import noctem.userService.domain.user.entity.RequiredInfo;
import noctem.userService.domain.user.entity.UserAccount;
import noctem.userService.domain.user.entity.UserPrivacy;
import noctem.userService.domain.user.repository.UserRepository;
import noctem.userService.global.common.CommonException;
import noctem.userService.global.enumeration.Sex;
import noctem.userService.global.security.bean.ClientInfoLoader;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ClientInfoLoader clientInfoLoader;

    @Override
    public Boolean signUp(SignUpReqDto dto) {
        // dto validate
        dto.dataformatMatching();
        // 성별 변환
        Sex sex;
        switch (dto.getRrnBackFirst()) {
            case "1":
            case "3":
            case "5":
            case "7":
                sex = Sex.MALE;
                break;
            case "2":
            case "4":
            case "6":
            case "8":
                sex = Sex.FEMALE;
                break;
            default:
                throw CommonException.builder().errorCode(2011).httpStatus(HttpStatus.BAD_REQUEST).build();
        }
        // 생년월일 변환
        String rrnYear = dto.getRrnFront().substring(0, 2);
        String rrnMonth = dto.getRrnFront().substring(2, 4);
        String rrnDay = dto.getRrnFront().substring(4);

        UserAccount userAccount = UserAccount.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .nickname(dto.getNickname())
                .build();

        UserPrivacy userPrivacy = UserPrivacy.builder()
                .name(dto.getName())
                .birthdayYear(dto.getRrnFront().substring(0, 2))
                .birthdayMonth(dto.getRrnFront().substring(2, 4))
                .birthdayDay(dto.getRrnFront().substring(4))
                .sex(sex)
                .build();

        RequiredInfo requiredInfo = RequiredInfo.builder()
                .termsOfServiceAgreement(dto.getTermsOfServiceAgreement())
                .personalInfoAgreement(dto.getPersonalInfoAgreement())
                .build();

        OptionalInfo optionalInfo = OptionalInfo.builder()
                .advertisementAgreement(dto.getAdvertisementAgreement())
                .build();

        userAccount.linkToUserPrivacy(userPrivacy)
                .linkToRequiredInfo(requiredInfo)
                .linkToOptionalInfo(optionalInfo);

        userRepository.saveUserAccount(userAccount);
        return true;
    }

    @Transactional(readOnly = true)
    @Override
    public Boolean duplCheckEmail(String email) {
        return userRepository.existEmail(email);
    }

    @Transactional(readOnly = true)
    @Override
    public Boolean duplCheckNickname(String nickname) {
        return userRepository.existNickname(nickname);
    }

    @Transactional(readOnly = true)
    @Override
    public Boolean isDarkmode() {
        return userRepository.isDarkmode(clientInfoLoader.getUserAccountId());
    }

    @Override
    public Boolean changeDarkmode() {
        OptionalInfo optionalInfo = userRepository.findOptionalInfoByUserAccountId(clientInfoLoader.getUserAccountId());
        optionalInfo.changeDarkmode();
        return true;
    }

    @Override
    public void updateLastAccessTime(Long userAccountId) {
        userRepository.updateLastAccessTime(userAccountId);
    }
}
