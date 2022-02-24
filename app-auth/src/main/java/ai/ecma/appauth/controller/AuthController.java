package ai.ecma.appauth.controller;

import ai.ecma.appauth.payload.*;
import ai.ecma.appauth.utils.RestConstants;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RequestMapping(value = AuthController.AUTH_CONTROLLER_PATH)
public interface AuthController {

    String AUTH_CONTROLLER_PATH = RestConstants.BASE_PATH_V1 + "openAuth/";
    String CHECK_USER_PATH = "check-user";
    String SIGN_UP_PATH = "sign-up";
    String SIGN_IN_PATH = "sign-in";
    String SEND_SMS_FOR_REGISTER_PATH = "send-sms-or-email-code-forgot-password";
    String CHECK_SMS_CODE_FOR_SIGN_IN_PATH = "check-sms-code-sign-in";
    String GET_SMS_CODE_BY_ID = "for-test-sms-code";
    String FORGOT_PASWORD_PATH = "forgot-password";
    String CHECK_PASSWORD_PATH = "check-password";
    String SET_PASSWORD_PATH = "set-password";
    String CHECK_TWO_STEP_VERIFICATION_PASWORD_PATH = "check-two-step-verification-password";
    String FORGOT_TWO_STEP_VERIFICATION_PASWORD_PATH = "forgot-two-step-verification-password";
    String RESET_TWO_STEP_VERIFICATION_PASSWORD_PATH = "reset-two-step-verification-password";
    String SEND_SMS_OR_EMAIL_CODE_FOR_FORGOT_PASSWORD_PATH = "send-sms-or-email-code-forgot-password";
    String CHECK_SMS_CODE_FOR_FORGOT_PASSWORD_PATH = "check-sms-code-forgot-password";
    String CHECK_EMAIL_CODE_FOR_FORGOT_PASSWORD_PATH = "check-email-code-forgot-password";
    String CHECK_EMAIL_CODE_FOR_TWO_STEP_VERIFICATION_FORGOT_PASSWORD_PATH = "check-email-code-two-step-forgot-password";
    String CHECK_USER_QUESTION = "check-user-question";
    String RESET_PASSWORD_PATH = "reset-password";
    String FORGOT_LOGIN_PATH = "forgot-login";

    //DONE

    /**
     * USER TIZIMGA KIRISHNI TANLAGANDA AVVAL TELEFON RAQAMNI YUBORADI. AGAR BUNDAY USER BO'LSA VA UNI PASSWORDI O'RNATILGAN BO'LSA,
     * registered va hasPassword FILDLARINI true QILIB QAYTARAMIZ, RO'YXATDAN O'TGAN-U AMMO PAROLI O'RNATILMAGAN BO'LSA registered TRUE va hasPassword FALSE QILIB QAYTARAMIZ.
     * AKS HOLDA registered va hasPassword FIELDLARINI FALSE HOLATDA QAYTARAMIZ
     *
     * @param checkPhoneDTO @RequestBody
     * @return ApiResult<SmsCodeDTO>
     */
    @ApiOperation(value = "Telefon raqam orqali userni ro'yxatdan o'tganligini tekshirish")
//    @ApiResponses(value = {
//            @ApiResponse(code = 201, message = "CheckPhone. 'data' field of response will have token which you can use to call other methods", response = ApiResult.class),
//            @ApiResponse(code = 409, message = "Bla. 'data' field of response will have token which you can use to call other methods", response = ApiResult.class)
//    })
    @PostMapping(CHECK_USER_PATH)
    ApiResult<CheckUserDTO> checkUser(@Valid @RequestBody CheckPhoneDTO checkPhoneDTO);


    /**
     * USER RO'YXATDAN O'TAYOTGANDA TELEFON RAQAM AVVAL TIZIMDA BOR BO'LSA XATOLIK
     * AKS HOLDA UNGA SMS YUBORAMIZ
     *
     * @param checkPhoneDTO @RequestBody
     * @return ApiResult<SmsCodeDTO>
     */
    @PostMapping(SEND_SMS_FOR_REGISTER_PATH)
    ApiResult<SmsCodeDTO> sendSmsForRegisterPath(@Valid @RequestBody CheckPhoneDTO checkPhoneDTO);


    @GetMapping(value = GET_SMS_CODE_BY_ID)
    ApiResult<String> getSmsCodeBySmsCodeId(@RequestParam String password, @RequestParam UUID id);

    /**
     * USER RO'YXATDAN O'TKAZISH, SMS CODE TO'G'RI BO'LSA SAVE QILAMIZ,
     * AKS HOLDA XATOLIK QAYATARAMIZ
     *
     * @param signUpDTO @RequestBody
     * @return ApiResult<TokenDTO>
     */
    @PostMapping(SIGN_UP_PATH)
    ApiResult<TokenDTO> signUp(@Valid @RequestBody SignUpDTO signUpDTO);

    /**
     * ILGARI RO`YXATDAN O`TGAN  USERNI TIZIMGA KIRITISH:
     * AGAR ESKI (USER OLDIN TIZIMGA SHU QURILMA ORQALI KIRGAN) DEVICEDAN
     * TIZIMGA KIRMOQCHI BO'LSA  SHU YO`LGA KELADI
     *
     * @param signInDTO @RequestBody
     * @return ApiResult<?>
     */
    @PostMapping(SIGN_IN_PATH)
    ApiResult<?> signIn(@Valid @RequestBody SignInDTO signInDTO);

    /**
     * ILGARI RO`YXATDAN O`TGAN  USERNI TIZIMGA KIRITISH:
     * AGAR YANGI (USER OLDIN TIZIMGA USHBU QURILMADAN UMUMAN KIRMAGAN) DEVICEDAN
     * TIZIMGA KIRMOQCHI BO'LSA  SHU YO`LGA KELADI
     * <p>
     * (BUNDA LOGIN VA PAROLDAN TASHQARI SMS KODNI TASDIQLASH HAM SO`RALADI)
     *
     * @param smsCodeDTO @RequestBody
     * @return ApiResult<TokenDTO>
     */
    @PostMapping(CHECK_SMS_CODE_FOR_SIGN_IN_PATH)
    ApiResult<TokenDTO> signInWithSmsCode(@Valid @RequestBody SmsCodeDTO smsCodeDTO);

    /**
     * USER PAROLINI UNUTGANDA (1-bosqich):
     * BUNDA TELEFON RAQAM KIRITILADI.
     * AGAR USHBU TELEFON RAQAMLI USER BO'LADIGAN BO'LSA, QUYDAGI HOLATLARDAN BIRI BO'LADI:
     * 1. AGAR DB DA TOPILGAN USER NING EMAILI VA XAVFSIZLIK SAVOLLARIGA BERGAN JAVOBLARI TEKSHIRILADI. SHULAR BO'LMASA TELEFONIGA SMS YUBORILADI
     * VA FRONTENDGA hasEmail FALSE, hasQuestion FALSE, sentSms TRUE, smsCode OBJECTI ICHIDA smsCodeId FIELDI KETADI VA METHOD TUGAYDI;
     * 2. AGAR EMAILI BO'LSA hasEmail TRUE, XAVFSIZLIK SAVOLLARIGA BERGAN JAVOBLARI BO'LSA hasQuestion TRUE, sentSms FALSE,
     * smsCode OBJECTI NULL KETADI VA METHOD TUGAYDI;
     * 3. AGAR EMAILI BO'LSA hasEmail TRUE, hasQuestion FALSE, sentSms FALSE, smsCode OBJECTI NULL KETADI VA METHOD TUGAYDI;
     * 4. AGAR XAVFSIZLIK SAVOLLARIGA BERGAN JAVOBLARI BO'LSA hasQuestion FALSE, hasEmail FALSE, sentSms FALSE, smsCode OBJECTI NULL KETADI VA METHOD TUGAYDI;
     *
     * @param checkPhoneDTO @RequestBody
     * @return ApiResult<ForgotPasswordDTO>
     */
    @PostMapping(FORGOT_PASWORD_PATH)
    ApiResult<ForgotPasswordDTO> forgotPassword(@Valid @RequestBody CheckPhoneDTO checkPhoneDTO);

    /**
     * USER PAROLINI UNUTGANDA (2.1-bosqich):
     * BUNDA TEL.RAQAMGA MIJOZ TELEFONGA SMS YUBORISH YOKI EMAIL GA XABAR YUBORISHNI TANLAGAN BO'LADI
     *
     * @param forgotPasswordDTO @RequestBody
     * @return ApiResult<ForgotPasswordDTO
     */
    @PostMapping(SEND_SMS_OR_EMAIL_CODE_FOR_FORGOT_PASSWORD_PATH)
    ApiResult<ForgotPasswordDTO> sendSmsOrEmailCodeForForgotPassword(@Valid @RequestBody ForgotPasswordDTO forgotPasswordDTO);

    /**
     * USER PAROLINI UNUTGANDA (2.2-bosqich):
     * BUNDA TEL.RAQAMGA KELGAN SMS KOD TEKSHIRILADI VA TEKSHIRILDI DEB BELGILANADI
     *
     * @param smsCodeDTO @RequestBody
     * @return ApiResult<SmsCodeDTO>
     */
    @PostMapping(CHECK_SMS_CODE_FOR_FORGOT_PASSWORD_PATH)
    ApiResult<SmsCodeDTO> checkSmsCodeForForgotPassword(@Valid @RequestBody SmsCodeDTO smsCodeDTO);

    /**
     * USER PAROLINI UNUTGANDA (2.2-bosqich):
     * BUNDA EMAILGA KELGAN KOD TEKSHIRILADI VA TASDIQLANADI
     *
     * @param resetPasswordDTO @RequestBody
     * @return ApiResult<Boolean>
     */
    @ApiOperation(value = "Telefon raqam orqali userni ro'yxatdan o'tganligini tekshirish")
    @PostMapping(CHECK_EMAIL_CODE_FOR_FORGOT_PASSWORD_PATH)
    ApiResult<Boolean> checkEmailCodeForForgotPassword(@Valid @RequestBody ResetPasswordDTO resetPasswordDTO);

    /**
     * USER PAROLINI UNUTGANDA (2.3-bosqich):
     * USER PAROLINI UNUTGANDA XAVFSIZLIK SAVOLIGA BERILGAN JAVOBNING TO'G'RILIGI TEKSHIRADI,
     * AGAR USER BIRINCHI SAVOLNI OLISH UCHUN KELGAN BO'LSA, UNI JAVOBI BO'LMAGANLIGI SABABLI TEKSHIRILMAYDI VA USHUBU USERNING 1-SAVOLI BERIB YUBORILADI.
     * AGAR USER BIROR BIR SAVOLGA JAVOB BERGAN BO'LSA VA  JAVOB TO'G'RI BO'LSA VA USHBU SAVOL OXIRGI SAVOL BO'LMASA KEYINGI SAVOL BERILADI.
     * AGAR OXIRGI SAVOL BO'LSA UserQuestionDTO DAGI finished TRUE QILIB MIJOZGA YUBORILADI
     *
     * @param userQuestionCheckDTO @RequestBody
     * @return ApiResult<UserQuestionCheckDTO>
     */
    @PostMapping(CHECK_USER_QUESTION)
    ApiResult<UserQuestionCheckDTO> checkUserQuestionAndReturnNextIfLastFinishedTrueOrGetFirstQuestion(@Valid @RequestBody UserQuestionCheckDTO userQuestionCheckDTO);

    /**
     * USER PAROLINI UNUTGANDA (3-bosqich):
     * BUNDA YANGI PASSWORD HAMDA PRE_PASSWORD 2.1-BOSQICHDAGI SMS YOKI 2.2-BOSQICHDAGI EMAIL_CODE YOKI 2.3-BOSQICHDAGI XAVFSIZLIK SAVOLLARI  BILAN BIRGALIKDA TEKSHIRILADI
     * HAMMASI TO'G'RI BO'LSA PAROL O`ZGARTIRILADI AKS HOLDA XATOLIK
     *
     * @param resetPasswordDTO @RequestBody
     * @return ApiResult<TokenDTO>
     */
    @PostMapping(RESET_PASSWORD_PATH)
    ApiResult<TokenDTO> resetPassword(@Valid @RequestBody ResetPasswordDTO resetPasswordDTO);

    /**
     * USER LOGINNI UNUTGANDA, EMAIL KELADI. USHBU EMAILGA ACCOUNT OCHILGAN BO'LSA, EMAILGA TELEFON RAQAM XABAR QILIB YUBORILADI
     *
     * @param email @RequestParam
     * @return ApiResult<String>
     */
    @PostMapping(FORGOT_LOGIN_PATH)
    ApiResult<String> forgotLogin(@RequestParam String email);


    /**
     * USER TIZIMGA KIRADIGAN PAROLINI UNUTGANDA, UNING TELEFONIGA SMS BORADI. SMS KIRITGACH VA SMS TO'G'RI BO'LGACH, AGAR USERDA TWO STEP VERIFICATION YOQILGAN BO'LSA,
     * USERDAN TWO STEP VERIFICATION PAROLINI SO'RAYMIZ. USER TWO STEP VERIFICATION NING PAROLINI KIRITGANDA UNI DB DAGI TWO STEP VERIFICATION PASSWORD BILAN SOLISHTIRIAMIZ
     * TELEFON RAQAM KELMASA, USERNI CONTEXTDAN OLAMIZ
     *
     * @param signInDTO @RequestBody
     * @return ApiResult<Boolean>
     */
    @PostMapping(CHECK_TWO_STEP_VERIFICATION_PASWORD_PATH)
    ApiResult<Boolean> checkTwoStepVerificationPaswordPath(@Valid @RequestBody SignInDTO signInDTO);

    /**
     * USER TIZIMGA KIRADIGAN PAROLINI UNUTGANDA, UNING TELEFONIGA SMS BORADI. SMS KIRITGACH VA SMS TO'G'RI BO'LGACH, AGAR USERDA TWO STEP VERIFICATION YOQILGAN BO'LSA,
     * USERDAN TWO STEP VERIFICATION PAROLINI SO'RAYMIZ. USER TWO STEP VERIFICATION NING PAROLINI HAM UNUTGAN BO'LSA SHU YO'LGA KELADI.
     * BIZ USERNING TWO STEP VERIFICATION UCHUN QO'YGAN EMAILGA TASDIQLASH KODI YUBORAMIZ.
     *
     * @param checkPhoneDTO @RequestBody
     * @return ApiResult<String>
     */
    @PostMapping(FORGOT_TWO_STEP_VERIFICATION_PASWORD_PATH)
    ApiResult<String> forgotTwoStepVerificationPaswordPath(@Valid @RequestBody CheckPhoneDTO checkPhoneDTO);


    /**
     * USER TWO STEP VERIFIACTION PAROLNI UNUTGANDA UNING EMAIL GA YUBORGAN TASDIQLASH KODINI YUBORIB TEKSHIRIB OLYAPTI TO'G'RILIGINI.
     * AGAR CODE TO'G'RI BO'LSA, UNGA YANGI PAROL QO'YISHGA RUXSAT BERADIGAN PAGE OCHILADI
     *
     * @param resetPasswordDTO @RequestBody
     * @return ApiResult<Boolean>
     */
    @ApiOperation(value = "Telefon raqam orqali userni ro'yxatdan o'tganligini tekshirish")
    @PostMapping(CHECK_EMAIL_CODE_FOR_TWO_STEP_VERIFICATION_FORGOT_PASSWORD_PATH)
    ApiResult<Boolean> checkEmailCodeForTwoStepVerificationForgotPassword(@Valid @RequestBody ResetPasswordDTO resetPasswordDTO);


    /**
     * FOYDALANUVCHI TWO STEP VERIFICATION PAROLNI UNUTGANDANDAN KEYIN UNING EMAIL IGA YUBORILGAN CODE NI VA YANGI PAROLLARNI YUBORADI.
     * HAMMASI TO'G'RI BO'LSA, PAROLLARNI YANGIDAN O'RNATAMIZ
     *
     * @param twoStepVerificationDTO @RequestBody
     * @return ApiResult<String>
     */
    @PatchMapping(path = RESET_TWO_STEP_VERIFICATION_PASSWORD_PATH)
    ApiResult<String> resetTwoStepVerificationPassword(@Valid @RequestBody TwoStepVerificationDTO twoStepVerificationDTO);


    /**
     * FOYDALANUVCHI RO'YXATDAN O'TKAZILGAN, AMMO UNING PAROLI NULL BO'LSA (OPERATOR TOMONIDAN RO'YXATGA OLINGAN HOLATDA),
     * PAROLLARINI BIZNING QOIDA BO'YICHA EKANLIGI TEKSHIRILADI. AGAR TO'G'RI BO'LSA USHUBU TELEFON RAQAMGA SMS YUBORILADI.
     *
     * @param resetPasswordDTO @RequestBody
     * @return ApiResult<String>
     */
    @PatchMapping(path = CHECK_PASSWORD_PATH)
    ApiResult<SmsCodeDTO> checkPassword(@Valid @RequestBody ResetPasswordDTO resetPasswordDTO);


    /**
     * FOYDALANUVCHI RO'YXATDAN O'TKAZILGAN, AMMO UNING PAROLI NULL BO'LSA (OPERATOR TOMONIDAN RO'YXATGA OLINGAN HOLATDA),
     * PAROLLARINI BIZNING QOIDA BO'YICHA EKANLIGI TEKSHIRILADI VA USHBU RAQAMGA YUBORILGAN SMS CODE TEKSHIRILADI.
     * HAMMASI TO'G'RI BO'LSA USERGA PAROL O'RNATILADI
     *
     * @param resetPasswordDTO @RequestBody
     * @return ApiResult<String>
     */
    @PatchMapping(path = SET_PASSWORD_PATH)
    ApiResult<TokenDTO> setPassword(@Valid @RequestBody ResetPasswordDTO resetPasswordDTO);


    //todo oauth2 ni qo'shish
}
