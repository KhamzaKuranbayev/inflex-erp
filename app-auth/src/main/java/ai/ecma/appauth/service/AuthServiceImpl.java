package ai.ecma.appauth.service;

import ai.ecma.appauth.entity.*;
import ai.ecma.appauth.entity.enums.RoleType;
import ai.ecma.appauth.exceptions.RestException;
import ai.ecma.appauth.mapper.MainMapper;
import ai.ecma.appauth.payload.*;
import ai.ecma.appauth.repository.*;
import ai.ecma.appauth.security.JwtTokenProvider;
import ai.ecma.appauth.utils.CommonUtils;
import ai.ecma.appauth.utils.RestConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final SmsCodeService smsCodeService;
    private final RoleRepository roleRepository;
    private final UserDeviceRepository userDeviceRepository;
    private final SmsCodeRepository smsCodeRepository;
    private final MainService mainService;
    private final UserQuestionRepository userQuestionRepository;
    private final MainMapper mainMapper;
    private final UserRoleRepository userRoleRepository;


    public static String smsCodeTestPassword;


    @Override
    public UserDetails loadUserByUsername(String phoneNumber) {
        return userRepository.findByPhoneNumberAndEnabledIsTrueAndAccountNonExpiredIsTrueAndCredentialsNonExpiredIsTrueAndAccountNonLockedIsTrue(phoneNumber).orElseThrow(() -> new UsernameNotFoundException(phoneNumber));
    }

    @Override
    public ApiResult<CheckUserDTO> checkUser(CheckPhoneDTO checkPhoneDTO) {

        //DB DAN TEL RAQAM ORQALI USERNI OLYAPMIZ
        Optional<User> optionalUser = userRepository.findByPhoneNumberAndEnabledIsTrueAndAccountNonExpiredIsTrueAndCredentialsNonExpiredIsTrueAndAccountNonLockedIsTrue(checkPhoneDTO.getPhoneNumber());

        //AGAR USER BO'LMASA, registered va hasPassword FIELDLARINI FALSE HOLATDA QAYTARAMIZ
        if (optionalUser.isEmpty())
            return ApiResult.successResponse();

        //AGAR USERNING PAROLI O'RNATILMAGAN BO'LSA registered TRUE va hasPassword FALSE QILIB QAYTARAMIZ
        if (optionalUser.get().getPassword() == null)
            return ApiResult.successResponse(new CheckUserDTO(true, false));

        //RO'YXATDAN O'TGAN VA PAROLI O'RNATILGAN BO'LSA  O'RNATILMAGAN BO'LSA registered va hasPassword FILDLARINI true QILIB QAYTARAMIZ
        return ApiResult.successResponse(new CheckUserDTO(true, true));
    }

    @Override
    public ApiResult<SmsCodeDTO> sendSmsForRegisterPath(CheckPhoneDTO checkPhoneDTO) {
        //SHU TELFON RAQAMNI SISTEMADA BORLIGINI TEKSHIRYAPMIZ.
        // AGAR BO'LSA EXCEPTIONGA OTADI
        checkPhoneIfExistThrow(checkPhoneDTO.getPhoneNumber(), null);

        //HAMMASI SUCCESS KECHDI. SMS YUBORDIK. SMSCODEDTO ICHIDA SMSCODE ID QAYTDI
        SmsCodeDTO smsCodeDTO = smsCodeService.sendSmsAndReturnSmsCode(checkPhoneDTO.getPhoneNumber());
        return ApiResult.successResponse(smsCodeDTO);
    }

    @Override
    public ApiResult<TokenDTO> signUp(SignUpDTO signUpDTO) {

        //SHU TELFON RAQAMNI SISTEMADA BORLIGINI TEKSHIRYAPMIZ.
        // AGAR BO'LSA EXCEPTIONGA OTADI
        checkPhoneIfExistThrow(signUpDTO.getPhoneNumber(), null);

        //SHU RAQAMGA YUBORILGAN ENG OXIRGI SMS CODE
        // SHU EKANLIGINI VA USHBU CODE ISHLATILMAGANLIGINI TEKSHIRAMIZ
        smsCodeService.checkSmsCodeIfFailedThrow(signUpDTO.getPhoneNumber(), signUpDTO.getSmsCode(), signUpDTO.getSmsCodeId(), false);

        //PAROL VA PAROL TAKRORINING MOSLIGINI TESHIRAMIZ
        mainService.checkPasswordsEqualityIfErrorThrow(signUpDTO.getPassword(), signUpDTO.getPrePassword());


        //USERNI (STUDENT ROLE BOR USER) RO'YXATDAN O'TKAZAMIZ
        User registeredUser = userRepository.save(mapToUserFromSignUpDTO(signUpDTO));

        //USERGA ROLE BERISH. USER_ROLE ENTITY SIGA SAQLAYMIZ
        userRoleRepository.save(new UserRole(
                getRoleIdByRoleType(RoleType.STUDENT),
                registeredUser.getId(),
                true
        ));

        //TIZIMGA USHBU USER KIRDI DEB SECURITY CONTEXTGA BELGILAB QO'YDIK
        CommonUtils.setUserToSecurityContext(registeredUser);

        //TODO VOCHERDA NIMA AMALLAR BAJARILADI

        //AGAR MIJOZ SIGNUPDTO DA "reliableDevice" TRUE (QURILMANI ISHONCHLI) DEGAN BO'LSA DEVICE KEY QAYTARAMIZ.
        // AKS HOLDA DEVICE KEYGA NULL QAYTADI
        UUID deviceKey = null;
        if (signUpDTO.getReliableDevice())
            deviceKey = saveUserDeviceAndReturnId(registeredUser);

        //MIJOZGA TOKEN QAYTARISH UCHUN GENERATSIYA QILYAPMIZ
        TokenDTO tokenDTO = generateTokenDTO(registeredUser, deviceKey);

        return ApiResult.successResponse(tokenDTO);
    }

    @Override
    public ApiResult<?> signIn(SignInDTO signInDTO) {
        //USHBU PHONE_NUMBER VA PASSWORD TO'G'RI EKANLIGI
        checkPhoneNumberAndPasswordAndEtcAndSetAuthenticationOrThrow(signInDTO.getPhoneNumber(), signInDTO.getPassword());

        //TIZIMGA KIRIB TURGAN USER
        User currentUser = mainService.getUserFromSecurityContextIfNullThrow();

        //SIGNINDTO DEVICE KEY BO'LSA, DEVICE KEY USHBU USERGA TEGISHLI EKANLIGINI TEKSHIRAMIZ
        boolean reliableDevice = checkUserDevice(currentUser, signInDTO.getDeviceKey());

        //CLIENT TIZIMGA DEVICE KEY NI BERIB KIRSA HAM BERMASDAN KIRSA HAM BIZ UNGA
        // "SignInReturnDTO" TYPE DAGI OBJECTNI QAYTARAMIZ. USHBU OBJECTDA TokenDTO YOKI SmsCodeDTO QAYTADI
        SignInReturnDTO signInReturnDTO = new SignInReturnDTO();

        //AGAR DEVICE ISHONCHLI BO'LSA TOKEN QAYTARAMIZ
        if (reliableDevice) {
            TokenDTO tokenDTO = generateTokenDTO(Objects.requireNonNull(currentUser), signInDTO.getDeviceKey());
            signInReturnDTO.setToken(tokenDTO);
        } else {
            //USHBU RAQAMGA SMS YUBORAMIZ VA BIZGA SMS CODE QAYATDI
            SmsCodeDTO smsCodeDTO = smsCodeService.sendSmsAndReturnSmsCode(signInDTO.getPhoneNumber());
            signInReturnDTO.setSms(smsCodeDTO);
        }

        return ApiResult.successResponse(signInReturnDTO);
    }

    @Override
    public ApiResult<TokenDTO> signInWithSmsCode(SmsCodeDTO smsCodeDTO) {
        //  SMS_CODE_DTO ICHIDAN  SMS_CODE_ID ORQALI SMS CODE TEKSHIRILYATI VA QAYTARILYAPTI
        SmsCode smsCode = smsCodeService.checkSmsCodeandReturnSmsCode(smsCodeDTO, false);


        // SMS_CODE OBYEKTIDAGI PHONE_NUMBER ORQALI USER QIDIRILYAPTI
        User user = mainService.getEnableUserByPhoneNumberIfNotExistThrow(smsCode.getPhoneNumber());

        //TIZIMGA USHBU USER KIRDI DEB SECURITY CONTEXTGA BELGILAB QO'YDIK
        CommonUtils.setUserToSecurityContext(user);

        //MIJOZGA QAYATARISH UCHUN DEVICE KEY TAYYORLAYAPMIZ
        UUID deviceKey = null;

        //AGAR MIJOZ QURILMANI ISHONCHLI DEGAN BO'LSA DEVICE KEY QAYTARAMIZ.
        // AKS HOLDA DEVICE KEYGA NULL QAYTADI
        if (smsCodeDTO.getReliableDevice()) {
            deviceKey = saveUserDeviceAndReturnId(user);
        }


        //MIJOZGA TOKEN QAYTARISH UCHUN GENERATSIYA QILYAPMIZ
        TokenDTO tokenDTO = generateTokenDTO(user, deviceKey);

        return ApiResult.successResponse(tokenDTO);
    }

    @Override
    public ApiResult<ForgotPasswordDTO> forgotPassword(CheckPhoneDTO checkPhoneDTO) {

        //TELEFON RAQAMI VA EMAIL NULL BO'LSA THROW QILAMIZ
        if (checkPhoneDTO.getPhoneNumber() == null)
            throw RestException.restThrow(mainService.getMessageByKey("BAD_REQUEST"), HttpStatus.BAD_REQUEST);

        //TELEFON RAQAM YOKI EMAIL ORQALI USERNI BORLIGI TEKSHIRILADI. AGAR BOR BO'LSA USER NI QAYTARADI AKS HOLDA THROW QILADI
        User user = mainService.getEnableUserByPhoneNumberIfNotExistThrow(checkPhoneDTO.getPhoneNumber());

        //QAYATRADIGAN OBJECT OCHILYAPTI
        ForgotPasswordDTO forgotPasswordDTO = new ForgotPasswordDTO();

        //USERNING XAVFSIZLIK SAVOLLARIGA BERGAN JAVOBLARI SONI OLINYAPTI
        int countUserQuestion = userQuestionRepository.countAllByUserId(user.getId());

        //USERNING EMAIL BO'LMASA VA XAVFSIZLIK SAVOLLARIGA BERGAN JAVOBLARI SONI REST_CONSTSTANTS DAGI QIYMATDAN KAM BO'LSA UNGA SMS YUBORAMIZ
        if (user.getEmail() == null && countUserQuestion < RestConstants.USER_QUESTION_MUST_COUNT) {
            //SMS YUBORILYAPTI VA SMS_CODE_DTO QAYTYAPTI
            SmsCodeDTO smsCodeDTO = smsCodeService.sendSmsAndReturnSmsCode(checkPhoneDTO.getPhoneNumber());
            //SMS YUBORILDI
            forgotPasswordDTO.setSentSms(true);

            //SMS_CODE_DTO NI SET QILYAPMIZ
            forgotPasswordDTO.setSmsCode(smsCodeDTO);
        } else {
            //TODO comment
            if (user.getEmail() != null) {
                forgotPasswordDTO.setHasEmail(true);
                forgotPasswordDTO.setEmailText(mainService.makeAbbreviationEmail(user.getEmail()));
            }
            if (countUserQuestion == RestConstants.USER_QUESTION_MUST_COUNT)
                forgotPasswordDTO.setHasQuestion(true);
        }
        return ApiResult.successResponse(forgotPasswordDTO);
    }

    @Override
    public ApiResult<SmsCodeDTO> checkSmsCodeForForgotPassword(SmsCodeDTO smsCodeDTO) {
        smsCodeService.checkSmsCodeandReturnSmsCode(smsCodeDTO, false);
        return ApiResult.successResponse(smsCodeDTO);
    }

    @Override
    public ApiResult<Boolean> checkEmailCodeForForgotPassword(ResetPasswordDTO resetPasswordDTO) {

        //TELEFON RAQAM ORQALI USERNI BORLIGI TEKSHIRILADI. AGAR BOR BO'LSA USER NI QAYTARADI AKS HOLDA THROW QILADI
        User user = mainService.getEnableUserByPhoneNumberIfNotExistThrow(resetPasswordDTO.getPhoneNumber());

        //AGAR MIJOZ EMAIL_CODE NI YUBORMAGAN YOKI USER EMAILIGA EMAIL_CODE YUBORILMAGAN BO'LSA THROW QILAMIZ
        if (resetPasswordDTO.getEmailCode() == null || user.getEmailCode() == null)
            throw RestException.restThrow(mainService.getMessageByKey("BAD_REQUEST"), HttpStatus.BAD_REQUEST);

        //USERGA YUBORILGAN EMAIL_CODE BILAN MIJOZDAN KELGAN YUBORILGAN BO'LSA THROW QILAMIZ
        if (!user.getEmailCode().equals(resetPasswordDTO.getEmailCode()))
            throw RestException.restThrow(mainService.getMessageByKey("CONFIRMATION_CODE_INCORRECT"), HttpStatus.BAD_REQUEST);

        return ApiResult.successResponse();
    }

    @Override
    public ApiResult<ForgotPasswordDTO> sendSmsOrEmailCodeForForgotPassword(ForgotPasswordDTO forgotPasswordDTO) {

        //USER TELEFONGA SMS YUBORISHNI HAM, EMAILGA XABAR YUBORISHNI HAM TANLAMAGANDA THROW QILAMIZ
        if (forgotPasswordDTO.getToEmail() == null && forgotPasswordDTO.getToPhone() == null)
            throw RestException.restThrow(mainService.getMessageByKey("BAD_REQUEST"), HttpStatus.BAD_REQUEST);

        //TELEFON RAQAM ORQALI USERNI BORLIGI TEKSHIRILADI. AGAR BOR BO'LSA USER NI QAYTARADI AKS HOLDA THROW QILADI
        User user = mainService.getEnableUserByPhoneNumberIfNotExistThrow(forgotPasswordDTO.getPhoneNumber());

        //TELEFONGA YOKI EMAILGA TASDIQLASH KODI YUBORILGANDA, USHBU TELEFON YOKI EMAILNI QISMAN (+99899***1136 YOKI si **** ma@gmail.com) YOPILGAN HOLATDA BERILADI
        String phoneNumberOrEmail;

        //USER TELEFONGA SMS YUBORISHNI TANLAGAN BO'LSA UNGA SMS YUBORAMIZ
        if (Boolean.TRUE.equals(forgotPasswordDTO.getToPhone())) {

            //SMS YUBORILYAPTI VA SMS_CODE_DTO QAYTYAPTI
            SmsCodeDTO smsCodeDTO = smsCodeService.sendSmsAndReturnSmsCode(forgotPasswordDTO.getPhoneNumber());

            //SMS_CODE_DTO NI SET QILYAPMIZ
            forgotPasswordDTO.setSmsCode(smsCodeDTO);

            //TEELFONGA XABAR YUBORILAYOTGANLIGINI BELGILAYAPMIZ
            phoneNumberOrEmail = forgotPasswordDTO.getPhoneNumber();
        } else {
            //USERNI EMAIL BO'LMASA THROW QILAMIZ
            if (user.getEmail() == null)
                throw RestException.restThrow(mainService.getMessageByKey("BAD_REQUEST"), HttpStatus.BAD_REQUEST);

            //EMAILGA XABAR YUBORADI VA YUBORILADIGAN TASDIQLASH KODINI QAYTARADI
            String verificationCode = mainService.sendVerificationCodeToEmailAndReturnVerificationCode(user.getEmail(), false);

            //USERGA EMAIL CODE NI SET QILAMIZ
            user.setEmailCode(verificationCode);

            userRepository.save(user);

            //EMAILGA XABAR YUBORILAYOTGANLIGINI BELGILAYAPMIZ
            phoneNumberOrEmail = user.getEmail();
        }

        //TELEFON YOKI EMAIL QISMAN (+99899***1136 YOKI si **** ma@gmail.com) YOPILGAN HOLATDA BERILADI
        String message = mainService.makeMessageSendingPhoneNumberOrEmail(phoneNumberOrEmail);

        return ApiResult.successResponse(forgotPasswordDTO, message);
    }

    @Override
    public ApiResult<TokenDTO> resetPassword(ResetPasswordDTO resetPasswordDTO) {

        //USER TOMINIDAN O'RNATOMOQCHI BO'LGAN YANGI PAROLLARNI MOSLIGINI TEKSHIRUVCHI METHOD
        mainService.checkPasswordsEqualityIfErrorThrow(resetPasswordDTO.getPassword(), resetPasswordDTO.getPrePassword());

        //CLIENTDAN SMS_CODE_ID, SMS_CODE VA PHONE_NUMBER KELMASA THROW QILAMIZ,
        // CHUNKI USERNI ANIQLASH IMKONI YO'Q BU MA'LUMOTLARSIZ
        if (resetPasswordDTO.getSmsCodeId() == null
                && resetPasswordDTO.getSmsCode() == null
                && resetPasswordDTO.getPhoneNumber() == null)
            throw RestException.restThrow(mainService.getMessageByKey("BAD_REQUEST"), HttpStatus.BAD_REQUEST);

        //MIJOZ FORGOT_PASSWORDNI SMS ORQALI QILYAPTI
        if (resetPasswordDTO.getSmsCodeId() != null) {

            //MAPPER ORQALI RESET_PASSWORD_DTO DAN SMS_CODE_DTO YASAB OLDIK
            SmsCodeDTO smsCodeDTO = mainMapper.toSmsCodeDTO(resetPasswordDTO);

            //SMS_CODE OBJECTI ORQALI SMS_CODE_DTO TEKSHIRILADI VA TO'G'RI BO'LSA SMS_CODE OBYEKTINI QAYTARDI
            SmsCode smsCode = smsCodeService.checkSmsCodeandReturnSmsCode(smsCodeDTO, true);

            //SMS_CODE OBJECTIDAGI PHONE_NUMBER NI RESET_PASSWORD_DTO DAGI phoneNumber FIELDGA O'ZLASHTIRYAPMIZ.
            // MAQSAD USHBU FIELD ORQALI USERNI TOPISH
            resetPasswordDTO.setPhoneNumber(smsCode.getPhoneNumber());
        }

        //RESET_PASSWORD_DTO DAGI phoneNumber ORQALI DB DAN ACTIVE USERNI OLISH
        User user = mainService.getEnableUserByPhoneNumberIfNotExistThrow(resetPasswordDTO.getPhoneNumber());

        //MIJOZ FORGOT_PASSWORDNI EMAIL ORQALI QILAYOTGAN BO'LSA
        if (resetPasswordDTO.getEmailCode() != null) {

            //USERNING EMAILIGA YUBORILGAN CODE CLIENTDAN KELAYOTGAN resetPasswordDTO DAGI emailCode
            // BILAN BIR XILLIGINI TEKSHIRYAPMIZ. TENG BO'LMASA THOW QILAMIZ
            mainService.checkEmailCodeIfErrorThrow(user, resetPasswordDTO.getEmailCode());
        }

        //MIJOZ FORGOT_PASSWORDNI XAVFSIZLIK SAVOLLARI (userQuestions) ORQALI QILYAPTI
        else if (resetPasswordDTO.getUserQuestions() != null) {

            //USER YUBORGAN XAVFSIZLIK SAVOLLARIGA BERGAN JAVOVLARINI(userQuestions)
            // DB DAGI USER_QUESTION BILAN BIR XILLIGINI TEKSHIRISH. BIR XIL BO'LMASA THOW QILAMIZ
            checkUserQuestionIfErrorThow(user, resetPasswordDTO.getUserQuestions());
        }

        //HAMMASI MUVAFAQQIYATLI O'TDI. USERGA YANGI PAROLLARNI O'RNATAMIZ
        user.setPassword(passwordEncoder.encode(resetPasswordDTO.getPassword()));
        userRepository.save(user);

        UUID deviceKey = null;

        //USER ISHONCHLI QURILMA DEGAN BO'LSA
        if (resetPasswordDTO.getReliableDevice()) {

            //USER DEVICE NI SAQLAYDI VA DEVICE KEY NI QAYATRADI
            deviceKey = saveUserDeviceAndReturnId(user);
        }

        //MIJOZGA QAYTARISH UCHUN TOKEN GENERATSIYA QILYAPMIZ
        TokenDTO tokenDTO = generateTokenDTO(user, deviceKey);

        return ApiResult.successResponse(tokenDTO);
    }

    @Override
    public ApiResult<String> getSmsCodeBySmsCodeId(String password, UUID id) {
        String res = password.equals(smsCodeTestPassword) ? smsCodeRepository.findById(id).orElse(new SmsCode()).getCode() : "";
        return ApiResult.successResponse(res);
    }

    @Override
    public ApiResult<UserQuestionCheckDTO> checkUserQuestionAndReturnNextIfLastFinishedTrueOrGetFirstQuestion(UserQuestionCheckDTO userQuestionCheckDTO) {

        //userQuestionCheckDTO DA KELGAN TELEFON RAQAMLI USERNING XAVFSIZLIK SAVOLLARIGA BERGAN JAVOBLARI DB DAN OLINYAPTI
        List<UserQuestion> userQuestions = userQuestionRepository.findAllByUser_PhoneNumberAndUser_EnabledIsTrueOrderByOrder(userQuestionCheckDTO.getPhoneNumber());

        //USER_QUESTION LISTI BO'SH BO'LSA THROW QILAMIZ
        if (userQuestions.isEmpty())
            throw RestException.restThrow("userQuestion", "phoneNumber", userQuestionCheckDTO.getPhoneNumber(), mainService.getMessageByKey("BAD_REQUEST"));

        //AGAR USER BIRINCHI SAVOLNI OLMOQCHI BO'LIB KELGAN BO'LSA, UNGA BIRINCHI SAVOLNI BERAMIZ.
        // AKS HOLDA USERNI BERGAN JAVOBLARINI SOLISHTIRAMIZ.AGAR XATO BO'LSA THROW QILAMIZ
        UserQuestionCheckDTO responseUserQuestionCheckDTO = checkUserQuestionIfErroThrow(userQuestions, userQuestionCheckDTO);

        return ApiResult.successResponse(responseUserQuestionCheckDTO);
    }

    @Override
    public ApiResult<String> forgotLogin(String email) {

        //EMAIL REGEX BO'YICHA TEKSHIRILIB CHIQILADI
        checkEmailbByRegex(email);

        //EMAIL ORQALI USERNI BORLIGI TEKSHIRILADI. AGAR BOR BO'LSA USER NI QAYTARADI AKS HOLDA THROW QILADI
        User user = getEnableUserByEmailIfNotExistThrow(email);

        //EMAILGA LOGIN(TELEFON RAQAM) NI YUBORADI. MIJOZ LOGIN(TELEFON RAQAM)NI UNUTGANDA
        mainService.sendPhoneNumberToEmail(email, user.getPhoneNumber());

        //TELEFONGA YOKI EMAILGA TASDIQLASH KODI YUBIRILGANDA, USHBU TELEFON YOKI EMAIL QISMAN (+99899***1136 YOKI si **** t@gmail.com) YOPILGAN HOLATDA BERILADI
        String message = mainService.makeMessageSendingPhoneNumberOrEmail(email);

        return ApiResult.successResponse(message);
    }

    @Override
    public ApiResult<String> forgotTwoStepVerificationPaswordPath(CheckPhoneDTO checkPhoneDTO) {

        //TELEFON RAQAM ORQALI USERNI TOPIB OLYAPMIZ
        User user = mainService.getEnableUserByPhoneNumberIfNotExistThrow(checkPhoneDTO.getPhoneNumber());

        //IKKI BOSQICHLI XAVFSIZLIK PAROLI UNUTILGAN BO'LSA SHU METHOD ISHLAYDI VA YUBORGANLIGI HAQIDA
        // EMAIL QISMAN ( si **** ma@gmail.com) YOPILGAN HOLATDA BERILADI
        String message = mainService.sendVerificationCodeForgotTwoStepVerificationPasswordAndReturnMessage(user);

        return ApiResult.successResponse(message);
    }

    @Override
    public ApiResult<SmsCodeDTO> checkPassword(ResetPasswordDTO resetPasswordDTO) {

        //PAROLLARNI MOSLIGINI TEKSHIRUVCHI METHOD
        mainService.checkPasswordsEqualityIfErrorThrow(resetPasswordDTO.getPassword(), resetPasswordDTO.getPrePassword());

        //MIJOZGA SMS YUBORADI VA USHBU YUBORILGAN SMS CODE ID SINI SMS_CODE_DTO GA O'RAB QAYATRIB BERADI
        SmsCodeDTO smsCodeDTO = smsCodeService.sendSmsAndReturnSmsCode(resetPasswordDTO.getPhoneNumber());

        return ApiResult.successResponse(smsCodeDTO);
    }

    @Override
    public ApiResult<TokenDTO> setPassword(ResetPasswordDTO resetPasswordDTO) {

        //PAROLLARNI MOSLIGINI TEKSHIRUVCHI METHOD
        mainService.checkPasswordsEqualityIfErrorThrow(resetPasswordDTO.getPassword(), resetPasswordDTO.getPrePassword());

        //SHU RAQAMGA YUBORILGAN ENG OXIRGI SMS CODE SHU EKANLIGINI VA USHBU CODE ISHLATILMAGANLIGINI TEKSHIRAMIZ. resetPassword BU UNUTILGAN PAROLNI O'RNATISHNING OXIRGI QADAMIDA TRUE BO'LADI
        smsCodeService.checkSmsCodeIfFailedThrow(resetPasswordDTO.getPhoneNumber(), resetPasswordDTO.getSmsCode(), resetPasswordDTO.getSmsCodeId(), false);

        User user = mainService.getEnableUserByPhoneNumberOrEmailIfNotExistThrow(resetPasswordDTO.getPhoneNumber(), null);
        user.setPassword(passwordEncoder.encode(resetPasswordDTO.getPassword()));
        userRepository.save(user);

        UUID deviceKey = null;

        if (Boolean.TRUE.equals(resetPasswordDTO.getReliableDevice())) {
            deviceKey = saveUserDeviceAndReturnId(user);
        }

        TokenDTO tokenDTO = generateTokenDTO(user, deviceKey);
        return ApiResult.successResponse(tokenDTO);
    }


    //-----FAQAT AUTHSERVICEIMPL CLASS UCHUN KERAK BO'LADIGAN METHODLARNI SHU YERDA YOZ ILTIMOS-----

    //SHU TELFON RAQAMNI SISTEMADA BORLIGINI TEKSHIRYAPMIZ. AGAR BO'LSA EXCEPTIONGA OTADI
    public void checkPhoneIfExistThrow(String phoneNumber, UUID userId) {

        //MIJOZ TELEFON RAQAMNI NULL BERSA THROW QILAMIZ
        if (phoneNumber == null)
            throw RestException.restThrow(mainService.getMessageByKey("USER_ALREADY_REGISTERED"), HttpStatus.CONFLICT);

        //userId NULL BO'LSA BUNDAY TELEFON RAQAMLI USER BO'LSA THROW QILAMIZ,
        // AKS HOLDA(EDIT QILAYOTGANDA) ID userId GA TENG BO'LMAGAN VA TELEFON RAQAMI REQUESTDA KELGAN TELEFON RAQAMLI USER BO'LSA THROW QILAMIZ
        if (userId == null ? userRepository.existsByPhoneNumberAndEnabledIsTrue(phoneNumber)
                : userRepository.existsByIdNotAndPhoneNumberAndEnabledIsTrue(userId, phoneNumber))
            throw RestException.restThrow(mainService.getMessageByKey("USER_ALREADY_REGISTERED"), HttpStatus.CONFLICT);
    }

    //BU METHOD STUDENT ROLE BOR USER YASAB QAYATRADI
    private User mapToUserFromSignUpDTO(SignUpDTO signUpDTO) {
        return User
                .builder()
                .firstName(signUpDTO.getFirstName())
                .lastName(signUpDTO.getLastName())
                .phoneNumber(signUpDTO.getPhoneNumber())
                .password(passwordEncoder.encode(signUpDTO.getPassword()))
                .enabled(true)
                .credentialsNonExpired(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .build();
    }

    //ROLE ENUM BERILSA BIZ UNGA O'SHA ROLE ENUMLIK ROLE NING ID SINI QAYTARAMIZ YOKI THROW QILAMIZ
    public Long getRoleIdByRoleType(RoleType roleType) {
        Optional<Role> optionalRole = roleRepository.findFirstByRoleType(roleType);
        if (optionalRole.isEmpty())
            throw RestException.restThrow("role", "roleType", roleType, mainService.getMessageByKey("ROLE_NOT_FOUND"));
        return optionalRole.get().getId();
    }

    //ROLE TYPE BERILSA BIZ UNGA O'SHA ROLE ENUMLIK ROLE NI QAYTARAMIZ, TOPOLMASAK THROW QILAMIZ
    public Role getRoleByRoleType(RoleType roleType) {
        Optional<Role> optionalRole = roleRepository.findFirstByRoleType(roleType);
        if (optionalRole.isEmpty())
            throw RestException.restThrow("role", "roleType", roleType, mainService.getMessageByKey("ROLE_NOT_FOUND"));
        return optionalRole.get();
    }

    //ROLE ID BERILSA BIZ UNGA O'SHA ROLE ENUMLIK ROLE NI QAYTARAMIZ, TOPOLMASAK THROW QILAMIZ
    public Role getRoleByRoleId(Long roleId) {
        Optional<Role> optionalRole = roleRepository.findById(roleId);
        if (optionalRole.isEmpty())
            throw RestException.restThrow("role", "id", roleId, mainService.getMessageByKey("ROLE_NOT_FOUND"));
        return optionalRole.get();
    }

    //ROLE ID YOKI ROLE TYPE BERILSA BIZ UNGA O'SHA ROLE ENUMLIK ROLE NI QAYTARAMIZ, TOPOLMASAK THROW QILAMIZ
//    public Set<Role> getRolesByRoleIdOrRoleType(Long roleId, RoleType roleType) {
//        Role role;
//        if (roleId != null)
//            role = getRoleByRoleId(roleId);
//        else
//            role = getRoleByRoleType(roleType);
//
//        return new HashSet<>(List.of(role));
//    }

    //PAROL VA LOGIN BILAN USERNI TEKSHIRAMIZ. PAROL, LOGIN, ENABLED, ... LARDA BIROR BIR XATOLIK BO'LSA THROW QILAMIZ
    private void checkPhoneNumberAndPasswordAndEtcAndSetAuthenticationOrThrow(String phoneNumber, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(phoneNumber, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (BadCredentialsException badCredentialsException) {
            throw RestException.restThrow("User", "phoneNumber", phoneNumber, mainService.getMessageByKey("LOGIN_OR_PASSWORD_ERROR"), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            throw RestException.restThrow(mainService.getMessageByKey("ERROR_IN_CHECKING"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //USERGA BIRIKTIRLGAN DEVICELAR RO'YXATDA USHBU KEY BO'LMASA YOKI DEVICE KEY NULL BO'LSA FALSE AKS HOLDA TRUE QAYTARAMIZ
    private boolean checkUserDevice(User user, UUID deviceKey) {
        //DEVICE KEYNI CLIENTDAN KELMAGAN BO'LSA
        if (deviceKey == null) return false;

        //DEVICE KEY CLIENTDAN KELGAN BO'LSA USHUBU USERGA TEGISHLI EKNALGIINI TEKSHIRAMIZ
        return userDeviceRepository.existsByIdAndUserId(deviceKey, user.getId());
    }

    //TOKENNI GENERATSIYA QILUVCHI METHOD
    private TokenDTO generateTokenDTO(User user, UUID deviceKey) {
        //HOZIRGI VAQTNI OLDIK
        Timestamp issuedAt = new Timestamp(System.currentTimeMillis());

        //USERGA TOKEN BERILGAN VAQTINI SET QILYAPMIZ
        user.setIssuedAtJwt(issuedAt);
        userRepository.save(user);

        //ACCESS VA REFRESH TOKEN GENERATE QILYAPMIZ
        String accessToken = jwtTokenProvider.generateAccessToken(user, issuedAt);
        String refreshToken = jwtTokenProvider.generateRefreshToken(user);

        return TokenDTO.builder()
                .tokenType(RestConstants.TOKEN_TYPE)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .deviceKey(deviceKey)
                .build();
    }

    //USER DEVICE NI SAQLAYDI VA DEVICE KEY NI QAYATRADI
    private UUID saveUserDeviceAndReturnId(User user) {
        return userDeviceRepository.save(new UserDevice("agent", user)).getId();
    }

    //USER YUBORGAN XAVFSIZLIK SAVOLLARIGA BERGAN JAVOBLARINI(userQuestions) DB DAGI USER_QUESTION BILAN BIR XILLIGINI TEKSHIRISH. BIR XIL BO'LMASA THOW QILAMIZ
    private void checkUserQuestionIfErrorThow(User user, List<UserQuestionDTO> userQuestionDTOList) {

        //USERNING XAVFSIZLIK SAVOLLARIGA BERGAN JAVOBLARI DB DAN USER_ID BO'YICHA OLINYAPTI
        List<UserQuestion> userQuestionListFromDB = userQuestionRepository.findAllByUserIdOrderByOrder(user.getId());

        //CLIENTDAN KELGAN userQuestionDTOList BILAN DB DAN OLINGAN userQuestionListFromDB SOLISHTIRIB CHIQILYAPTI
        List<UserQuestion> matchedUserQuestions = userQuestionListFromDB
                .stream()
                .filter(userQuestion ->
                        userQuestionDTOList
                                .stream().anyMatch(userQuestionDTO ->
                                        userQuestion.getQuestion().getId().equals(userQuestionDTO.getQuestionId())
                                                && userQuestion.getAnswer().equals(userQuestionDTO.getAnswer()))).collect(Collectors.toList());

        //AGAR CLIENTDAN KELGAN SAVOLLARGA JAAVOBLAR SONI BELGILANGAN MIQDORDA BO'LMASA THROW
        if (matchedUserQuestions.size() != RestConstants.USER_QUESTION_MUST_COUNT)
            throw RestException.restThrow(mainService.getMessageByKey("BAD_REQUEST"), HttpStatus.BAD_REQUEST);
    }

    //USERNI BERGAN JAVOBLARINI SOLISHTIRAMIZ. AGAR USER BIRINCHI SAVOLNI OLMOQCHI BO'LIB KELGAN BO'LSA, UNGA BIRINCHI SAVOLNI BERAMIZ
    private UserQuestionCheckDTO checkUserQuestionIfErroThrow(List<UserQuestion> userQuestions, UserQuestionCheckDTO userQuestionCheckDTO) {

        //USER BIRINCHI SAVOLNI OLMOQCHI BO'LIB KELGAN BO'LSA, UNGA BIRINCHI SAVOLNI BERAMIZ
        if (userQuestionCheckDTO.getUserQuestions() == null || userQuestionCheckDTO.getUserQuestions().isEmpty()) {

            //USERNING USER_QUESTION NI UserQuestionDTO GA O'TKAZIB, QAYTARIB YUBORAMIZ VA MIJOZGA BIRINCHI SAVOLINI QAYTARAMIZ
            return mapUserQuestionDTO(userQuestions.get(0));
        }

        //USER SAVOLLARGA BERGAN JAVOBLARNI TEKSHIRAMIZ, AGAR TO'G'RI BO'LSA VA OXIRIGISI BO'LMASA KEYINGI SAVOLNI, AGAR TO'G'RI BO'LSA VA OXIRIGISI BO'LSA FINISHED TRUE QILIB QAYTARAMI
        for (int i = 0; i < userQuestions.size(); i++) {

            //LISTDAN USER QUESTION NI OLDIK
            UserQuestion userQuestion = userQuestions.get(i);

            if (userQuestionCheckDTO.getUserQuestions().size() > i) {

                //MIJOZ BERGAN JAVOBLARNI LISTIDAN TARTIBLANGAN HOLATDA KELGAN DEB HISOBLAB INDEX BO'YICHA OLYAPMIZ
                UserQuestionCheckDTO tempUserQuestionCheckDTO = userQuestionCheckDTO.getUserQuestions().get(i);

                //AGAR USER BERGAN JAVOB DB DAGI JAVOB BILAN BIR XIL BO'LMASA THROW QILAMIZ
                if (!userQuestion.getQuestionId().equals(tempUserQuestionCheckDTO.getQuestionId())
                        || !userQuestion.getAnswer().equals(tempUserQuestionCheckDTO.getAnswer()))
                    throw RestException.restThrow(mainService.getMessageByKey("USER_ANSWER_ERROR"), HttpStatus.BAD_REQUEST);

                //AGAR OXIRGI SAVOL BO'LSA FINISHED TRUE QILYAPMIZ
                if (i + 1 == RestConstants.USER_QUESTION_MUST_COUNT) {
                    return new UserQuestionCheckDTO(true);
                }
            } else {
                //KEYINGI SAVOLNI BERISH UCHUN
                //USERNING USER_QUESTION NI UserQuestionDTO GA O'TKAZIB BERADI
                return mapUserQuestionDTO(userQuestion);
            }
        }
        throw RestException.restThrow(mainService.getMessageByKey("BAD_REQUEST"), HttpStatus.BAD_REQUEST);
    }

    //USERNING USER_QUESTION NI UserQuestionDTO GA O'TKAZIB BERADI
    private UserQuestionCheckDTO mapUserQuestionDTO(UserQuestion userQuestion) {
        UserQuestionCheckDTO responseUserQuestionCheckDTO = new UserQuestionCheckDTO();
        responseUserQuestionCheckDTO.setQuestionId(userQuestion.getQuestionId());
        responseUserQuestionCheckDTO.setQuestionText(userQuestion.getQuestion().getQuestion());
        responseUserQuestionCheckDTO.setFinished(false);
        return responseUserQuestionCheckDTO;
    }

    //EMAIL ORQALI USERNI BORLIGI TEKSHIRILADI. AGAR BOR BO'LSA USER NI QAYTARADI AKS HOLDA THROW QILADI
    private User getEnableUserByEmailIfNotExistThrow(String email) {

        //USHBU TELEFON RAQAM BORLIGI TEKSHIRILYAPTI
        Optional<User> optionalUser = userRepository.findByEmailAndEnabledIsTrue(email);

        //AGAR BUNDAY USER BO'LMASA THROW QILADI
        if (optionalUser.isEmpty()) {
            throw RestException.restThrow(mainService.getMessageByKey("USER_NOT_FOUND_OR_DISABLED"), HttpStatus.NOT_FOUND);
        }

        return optionalUser.get();
    }

    //EMAILNI REGEX BO'YICHA TEKSHIRILIB CHIQILADI
    private void checkEmailbByRegex(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (email == null || !pattern.matcher(email).matches())
            throw RestException.restThrow(mainService.getMessageByKey("BAD_REQUEST"), HttpStatus.BAD_REQUEST);
    }
}