package ai.ecma.appauth.service;

import ai.ecma.appauth.entity.User;
import ai.ecma.appauth.exceptions.RestException;
import ai.ecma.appauth.payload.ApiResult;
import ai.ecma.appauth.payload.ResetPasswordDTO;
import ai.ecma.appauth.payload.TwoStepVerificationDTO;
import ai.ecma.appauth.repository.UserRepository;
import ai.ecma.appauth.utils.CommonUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;


@Service
@RequiredArgsConstructor
public class MainService {
    private final MessageSource messageSource;
    private final MailService mailService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //PROPERTIES DAGI KEY NI BERSA MIJOZ TURGAN TILGA QARAB, XABARNI QAYTARADI
    public String getMessageByKey(String key) {
        return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
    }


    //TELEFONGA CODE NI XABAR QILIB YUBORISH UCHUN TEXT QAYTARADI
    public String generateVerificationCodeWithMessageToPhone(String code) {
        return getMessageByKey("SMS_CODE_WITH_TEXT") + code;
    }


    //EMAILGA 2 BOSQICHLI XAVFSIZLIK CODE NI XABAR QILIB YUBORISH UCHUN TEXT YASAB QAYTARADI METHOD
    public String generateVerificationCodeWithMessageToEmailTwoStepVerification(String code) {
        return getMessageByKey("VERIFICATION_CODE_WITH_TEXT_EMAIL_TWO_STEP_VERIFICATION") + code;
    }

    //EMAILGA LOGIN(TELEFON RAQAM)NI YUBORISH UCHUN TEXTNI TAYYORLAYDI
    public String generatePhoneNumberWithMessageToEmail(String phoneNumber) {
        return getMessageByKey("VERIFICATION_CODE_WITH_TEXT_EMAIL_TWO_STEP_VERIFICATION") + phoneNumber;
    }

    //EMAILGA TASDIQLASH CODE NI XABAR QILIB YUBORISH UCHUN TEXT YASAB QAYTARADI
    public String generateVerificationCodeWithMessageToEmail(String code) {
        return getMessageByKey("VERIFICATION_CODE_WITH_TEXT_EMAIL") + code;
    }


    //CODE GENERATE QILADI UNGA NECHTALIK QILISHNI BERIB YUBORSA
    public String generateVerificationCode(int countChar) {
        if (countChar > 8 || countChar < 5)
            throw RestException.restThrow(getMessageByKey("SMS_CODE_MUST_BE_BETWEEN"), HttpStatus.BAD_REQUEST);
        return String.valueOf(new Random().nextInt(999999999)).substring(0, countChar);
    }


    //PAROLLARNI MOSLIGINI TEKSHIRUVCHI METHOD
    public void checkPasswordsEqualityIfErrorThrow(@NonNull String password, @NonNull String prePassword) {
        if (!password.equals(prePassword))
            throw RestException.restThrow(getMessageByKey("PASSWORDS_NOT_EQUAL"), HttpStatus.BAD_REQUEST);
    }


    //USERGA YUBORILGAN EMAIL_CODE NI TEKSHIRADI
    public void checkEmailCodeIfErrorThrow(User user, String emailCode) {
        if (emailCode == null || !emailCode.equals(user.getEmailCode()))
            throw RestException.restThrow(getMessageByKey("CONFIRMATION_CODE_INCORRECT"), HttpStatus.BAD_REQUEST);
    }

    //TELEFONGA YOKI EMAILGA TASDIQLASH KODI YUBIRILGANDA, USHBU TELEFON YOKI EMAIL QISMAN (+99899***1136 YOKI si **** ma@gmail.com) YOPILGAN HOLATDA BERILADI.
    // si***t@gmail.com emailga kod yuborildi! || +99899 *** 1136 raqamiga kod yuborildi!
    public String makeMessageSendingPhoneNumberOrEmail(String phoneNumberOrEmail) {
        String resSubstring;

        //AGAR EMAIL BO'LSA
        if (phoneNumberOrEmail.contains("@")) {

            //USHBU EMAIL NI QISMAN (si **** t@gmail.com) YOPILGAN HOLATDA BERILADI
            resSubstring = makeAbbreviationEmail(phoneNumberOrEmail);

            resSubstring = resSubstring + " " + getMessageByKey("VERIFICATION_CODE_SENT_TO_EMAIL");
        } else {

            //USHBU TELEFON NI QISMAN (+99899***1136) YOPILGAN HOLATDA BERILADI
            resSubstring = makeAbbreviationPhoneNumber(phoneNumberOrEmail);

            resSubstring = resSubstring + " " + getMessageByKey("VERIFICATION_CODE_SENT_TO_PHONE_NUMBER");
        }
        return resSubstring;
    }


    //USHBU EMAIL NI QISMAN (si **** ma@gmail.com) YOPILGAN HOLATDA BERILADI
    public String makeAbbreviationEmail(String email) {
        return email.substring(0, 2) + "*****" + email.substring(email.indexOf("@") - 1);
    }

    //USHBU TELEFON NI QISMAN (+99899***1136) YOPILGAN HOLATDA BERILADI
    public String makeAbbreviationPhoneNumber(String phoneNumber) {
        return phoneNumber.substring(0, 6) + "*****" + phoneNumber.substring(phoneNumber.length() - 4);
    }

    //EMAILGA XABAR YUBORADI VA YUBORILADIGAN TASDIQLASH KODINI QAYTARADI
    public String sendVerificationCodeToEmailAndReturnVerificationCode(String email, boolean twoStepVerification) {

        //EMAILGA YUBORILADIGAN TASDIQLASH KODINI GENERATE QILDIK
        String verificationCode = generateVerificationCode(6);

        //twoStepVerification TRUE BO'LSA, 2 BOSQICHLI XAVFSIZLIK HAQIDA TEXT AKS HOLDA BOSHQA TEXT GENERATE QILADI
        String messageToEmail = twoStepVerification ?
                generateVerificationCodeWithMessageToEmailTwoStepVerification(verificationCode)
                : generateVerificationCodeWithMessageToEmail(verificationCode);

        //EMAILGA XABAR YUBORYAPMIZ
        mailService.sendEmailVerificationCode(messageToEmail, email);

        return verificationCode;
    }

    //EMAILGA LOGIN(TELEFON RAQAM) NI YUBORADI. MIJOZ LOGIN(TELEFON RAQAM)NI UNUTGANDA
    public void sendPhoneNumberToEmail(String email, String phoneNumber) {

        //EMAILGA USERNING TELEFON RAQAMINI TEXT HOLATIDA GENERATE QILADI
        String messageToEmail = generatePhoneNumberWithMessageToEmail(phoneNumber);

        //EMAILGA XABAR YUBORYAPMIZ
        mailService.sendEmailVerificationCode(messageToEmail, email);
    }

    public User getUserFromSecurityContextIfNullThrow() {
        User userFromSecurityContext = CommonUtils.getUserFromSecurityContext();
        if (userFromSecurityContext == null)
            throw RestException.restThrow(getMessageByKey("USER_NOT_FOUND"), HttpStatus.UNAUTHORIZED);
        return userFromSecurityContext;
    }

    //IKKI BOSQICHLI XAVFSIZLIK PAROLI UNUTILGAN BO'LSA SHU METHOD ISHLAYDI VA YUBORGANLIGI HAQIDA
    // EMAIL QISMAN ( si **** ma@gmail.com) YOPILGAN HOLATDA BERILADI
    public String sendVerificationCodeForgotTwoStepVerificationPasswordAndReturnMessage(User currentUser) {

        //USERDA IKKI BOSQICHLI XAVFSIZLIK ILGARI AKTIVLASHTIRILMAGAN BO`LSA THROW
        if (currentUser.getTwoStepVerification() == null || !currentUser.getTwoStepVerification())
            throw RestException.restThrow(getMessageByKey("TWO_STEP_VERIFICATION_NOT_ACTIVATED"), HttpStatus.BAD_REQUEST);

        //EMAILGA XABAR YUBORADI VA YUBORILADIGAN TASDIQLASH KODINI QAYTARADI
        String verificationCode = sendVerificationCodeToEmailAndReturnVerificationCode(currentUser.getEmailTwoStepVerification(), true);

        currentUser.setTwoStepVerficationCode(verificationCode);
        userRepository.save(currentUser);

        //TELEFON YOKI EMAIL QISMAN (+99899***1136 YOKI si **** ma@gmail.com) YOPILGAN HOLATDA BERILADI
        return makeMessageSendingPhoneNumberOrEmail(currentUser.getEmailTwoStepVerification());
    }

    public void checkTwoStepVerificationPassword(String phoneNumber, String password) {
        //SECURITY CONTEXTDAGI USERNI OLISH
        User userFromSecurityContext = CommonUtils.getUserFromSecurityContext();

        //SECURITY CONTEXTDAGI USER NULL BO'LMASA, SHU USERNI AKS HOLDA TELEFON RAQAM ORQALI USERNI OLAMIZ
        User user = userFromSecurityContext != null ? userFromSecurityContext : getEnableUserByPhoneNumberIfNotExistThrow(phoneNumber);

        //USERDA IKKI BOSQICHLI XAVFSIZLIK ILGARI AKTIVLASHTIRILMAGAN BO`LSA THROW
        checkTwoStepVerificationOnIfOffThrow(user);

        //USERNING PAROLI TO`G`RILIGINI TEKSHIRISH
        checkUserPassword(password, user.getTwoStepPassword());
    }

    //USERDA IKKI BOSQICHLI XAVFSIZLIK ILGARI AKTIVLASHTIRILMAGAN BO`LSA THROW
    public void checkTwoStepVerificationOnIfOffThrow(User user) {
        if (user.getTwoStepVerification() == null || !user.getTwoStepVerification())
            throw RestException.restThrow(getMessageByKey("TWO_STEP_VERIFICATION_NOT_ACTIVATED"), HttpStatus.BAD_REQUEST);
    }

    //USERNING PAROLI TO`G`RILIGINI TEKSHIRISH
    public void checkUserPassword(String enteringPassword, String userEncodedPassword) {
        if (!passwordEncoder.matches(enteringPassword, userEncodedPassword))
            throw RestException.restThrow(getMessageByKey("PASSWORDS_NOT_EQUAL"), HttpStatus.BAD_REQUEST);
    }

    public void checkHintContainsPassword(String hint, String password) {
        if (hint.contains(password))
            throw RestException.restThrow(getMessageByKey("PASSWORD_AND_HINT_EQUAL"), HttpStatus.BAD_REQUEST);
    }

    //TELEFON RAQAM YOKI EMAIL ORQALI USERNI BORLIGI TEKSHIRILADI. AGAR BOR BO'LSA USER NI QAYTARADI AKS HOLDA THROW QILADI
    public User getEnableUserByPhoneNumberOrEmailIfNotExistThrow(String phoneNumber, String email) {
        return phoneNumber != null ? getEnableUserByPhoneNumberIfNotExistThrow(phoneNumber) : getEnableUserByEmailIfNotExistThrow(email);
    }

    //TELEFON RAQAM ORQALI USERNI BORLIGI TEKSHIRILADI. AGAR BOR BO'LSA USER NI QAYTARADI AKS HOLDA THROW QILADI
    public User getEnableUserByPhoneNumberIfNotExistThrow(String phoneNumber) {

        //USHBU TELEFON RAQAM BORLIGI TEKSHIRILYAPTI
        Optional<User> optionalUser = userRepository.findByPhoneNumberAndEnabledIsTrueAndAccountNonExpiredIsTrueAndCredentialsNonExpiredIsTrueAndAccountNonLockedIsTrue(phoneNumber);

        //AGAR BUNDAY USER BO'LMASA THROW QILADI
        if (optionalUser.isEmpty()) {
            throw RestException.restThrow("User", "phoneNumber", phoneNumber, getMessageByKey("USER_NOT_FOUND"), HttpStatus.NOT_FOUND);
        }
        return optionalUser.get();
    }

    //EMAIL ORQALI USERNI BORLIGI TEKSHIRILADI. AGAR BOR BO'LSA USER NI QAYTARADI AKS HOLDA THROW QILADI
    public User getEnableUserByEmailIfNotExistThrow(String email) {

        //USHBU TELEFON RAQAM BORLIGI TEKSHIRILYAPTI
        Optional<User> optionalUser = userRepository.findByEmailAndEnabledIsTrue(email);

        //AGAR BUNDAY USER BO'LMASA THROW QILADI
        if (optionalUser.isEmpty()) {
            throw RestException.restThrow(getMessageByKey("USER_NOT_FOUND_OR_DISABLED"), HttpStatus.NOT_FOUND);
        }
        return optionalUser.get();
    }

    public ApiResult<String> resetTwoStepVerificationPassword(TwoStepVerificationDTO twoStepVerificationDTO) {

        //SECURITY CONTEXTDAGI USERNI OLISH
        User userFromSecurityContext = CommonUtils.getUserFromSecurityContext();

        //SECURITY CONTEXTDAGI USER NULL BO'LMASA, SHU USERNI AKS HOLDA TELEFON RAQAM ORQALI USERNI OLAMIZ
        User user = userFromSecurityContext != null ? userFromSecurityContext : getEnableUserByPhoneNumberIfNotExistThrow(twoStepVerificationDTO.getPhoneNumber());

        //EMAILGA YUBORILGAN TASDIQLOVCHI KODNING MOSLIGINI TEKSHIRISH
        checkCodeEqualsIfErrorThrow(twoStepVerificationDTO.getCode(), user.getTwoStepVerficationCode());

        user.setTwoStepVerficationCode(null);
        user.setTwoStepVerification(true);
        user.setTwoStepPassword(passwordEncoder.encode(twoStepVerificationDTO.getPassword()));
        user.setTwoStepPasswordHint(twoStepVerificationDTO.getHint());
        userRepository.save(user);

        return ApiResult.successResponse(getMessageByKey("PASSWORD_SUCCESSFULLY_CHANGED"));
    }

    public void checkCodeEqualsIfErrorThrow(String enteringCode, String userCode) {
        if (enteringCode == null || !enteringCode.equals(userCode))
            throw RestException.restThrow(getMessageByKey("CONFIRMATION_CODE_INCORRECT"), HttpStatus.BAD_REQUEST);

    }

    //USER TWO STEP VERIFIACTION PAROLNI UNUTGANDA UNING EMAIL GA YUBORGAN TASDIQLASH KODINI YUBORIB TEKSHIRIB OLYAPTI TO'G'RILIGINI.
    // AGAR CODE TO'G'RI BO'LSA, UNGA YANGI PAROL QO'YISHGA RUXSAT BERADIGAN PAGE OCHILADI
    public ApiResult<Boolean> checkEmailCodeForTwoStepVerificationForgotPassword(ResetPasswordDTO resetPasswordDTO) {

        //SECURITY CONTEXTDAGI USERNI OLISH
        User userFromSecurityContext = CommonUtils.getUserFromSecurityContext();

        //SECURITY CONTEXTDAGI USER NULL BO'LMASA, SHU USERNI AKS HOLDA TELEFON RAQAM ORQALI USERNI OLAMIZ
        User user = userFromSecurityContext != null ? userFromSecurityContext : getEnableUserByPhoneNumberIfNotExistThrow(resetPasswordDTO.getPhoneNumber());

        //AGAR MIJOZ EMAIL_CODE NI YUBORMAGAN YOKI USER EMAILIGA EMAIL_CODE YUBORILMAGAN BO'LSA THROW QILAMIZ
        if (resetPasswordDTO.getEmailCode() == null || user.getEmailCode() == null)
            throw RestException.restThrow(getMessageByKey("BAD_REQUEST"), HttpStatus.BAD_REQUEST);

        //USERGA YUBORILGAN EMAIL_CODE BILAN MIJOZDAN KELGAN YUBORILGAN BO'LSA THROW QILAMIZ
        if (!user.getEmailCode().equals(resetPasswordDTO.getEmailCode()))
            throw RestException.restThrow(getMessageByKey("CONFIRMATION_CODE_INCORRECT"), HttpStatus.BAD_REQUEST);

        return ApiResult.successResponse();
    }
}
