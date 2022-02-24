package ai.ecma.appauth.controller;

import ai.ecma.appauth.AppAuthApplication;
import ai.ecma.appauth.entity.SmsCode;
import ai.ecma.appauth.payload.*;
import ai.ecma.appauth.service.AuthServiceImpl;
import ai.ecma.appauth.utils.TestUtil;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@AutoConfigureMockMvc//MOCK NVC NI AUTO SOZLASH UCHUN, ONLY TESTING
@Import({ai.ecma.appauth.AppAuthApplication.class})
@ExtendWith(SpringExtension.class)//ONLY FOR TESTING
@AutoConfigureTestDatabase(connection =
        EmbeddedDatabaseConnection.NONE,
        replace = AutoConfigureTestDatabase.Replace.NONE)//DB NI AUTO CONFIG QILMA DEDIM
@TestInstance(TestInstance.Lifecycle.PER_CLASS)//TESTNING HAYOTI QANDAY KECHISHI
@SpringBootTest(classes = {AppAuthApplication.class})//QAYSI REAL LOYIHANI ICHINI TEST QILISHI KERAKLIGI
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)//METHODARNI TARTIB BILAN ISHALSHINI TA'MINLASH UCHUN
@DirtiesContext//CONTEXTDAN OBJECTLARNI TOZALAMA
//@PropertySource("classpath:application-test.yml ")
@ContextConfiguration
@ActiveProfiles("test")//QAYSI PROPERTIESDAN O'QISHINI AYTISH KERAK
class AuthControllerTest {
    private static final String password = "Root@123";
    private static final String prePassword = "Root@123";
    private static final String firstName = "Ketmon";
    private static final String lastName = "Teshayev";
    private static final String phoneNumber = "+998971234567";
    private static final String phoneNumber2 = "+998971234568";
    private static final String newPassword = "Root@1234";
    private static final String newPrePassword = "Root@1234";
    private String code;
    private UUID smsCodeId = UUID.randomUUID();
    private String forgotPasswordSmsCode;
    private UUID forgotPasswordSmsCodeId;

    private ArgumentCaptor<SmsCode> smsCodeArgumentCaptor = ArgumentCaptor.forClass(SmsCode.class);


    @Autowired
    private WebApplicationContext context;
    @Autowired
    MockMvc mockMvc;


    @BeforeAll
    public void initialiseRestAssuredMockMvcWebApplicationContext() {
        //CONFIG
        MockitoAnnotations.openMocks(this);
        RestAssuredMockMvc.standaloneSetup(MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity()));
    }

    @Order(value = 5)
    @Test
    public void checkSmsCodeTestPasswordIsNullSuccessCase() {
        Assertions.assertThat(AuthServiceImpl.smsCodeTestPassword).isNull();
    }

    @Order(value = 6)
    @Test
    public void checkSmsCodeTestPasswordNotNullSuccessCase() {
        AuthServiceImpl.smsCodeTestPassword = TestUtil.SMS_CODE_TEST_PASSWORD;
        Assertions.assertThat(AuthServiceImpl.smsCodeTestPassword).isNotNull();
    }

    @Order(value = 8)
    @Test
    public void checkSmsCodeTestPasswordIsNotEqualFailCase() {
        ApiResult<String> result = TestUtil.getSmsCodeBySmsCodeId(smsCodeId, UUID.randomUUID().toString(), HttpStatus.OK);
        TestUtil.validateSuccessApiResponse(result);
        Assertions.assertThat(result.getMessage()).isEqualTo("");
    }

    @Order(value = 10)
    @Test
    public void checkPhoneSuccessCase() {
        CheckPhoneDTO checkPhoneDTO = new CheckPhoneDTO(phoneNumber);
        ApiResult<SmsCodeDTO> result = TestUtil.checkPhone(checkPhoneDTO, HttpStatus.OK);
        TestUtil.validateSuccessApiResponse(result);
        smsCodeId = result.getData().getSmsCodeId();
        ApiResult<String> smsCode = TestUtil.getSmsCodeBySmsCodeId(smsCodeId, TestUtil.SMS_CODE_TEST_PASSWORD, HttpStatus.OK);
        code = smsCode.getMessage();
    }


    // Telefon raqam notog'ri tartibda kiritilsa exception berishini tekshirish
    @Order(value = 20)
    @Test
    void checkPhoneInvalidRegexCase() {
        CheckPhoneDTO checkPhoneDTO = new CheckPhoneDTO("+998$$$9712345671233123");
        ApiResult<SmsCodeDTO> result = TestUtil.checkPhone(checkPhoneDTO, HttpStatus.BAD_REQUEST);
        TestUtil.validateFailApiResponse(result);
    }

    // Bundat telefon raqami bazada allaqachon borligida exception berishini tekshirishini
    @Order(value = 40)
    @Test
    void checkPhoneFailCase() {
        CheckPhoneDTO checkPhoneDTO = new CheckPhoneDTO(phoneNumber);
        ApiResult<SmsCodeDTO> result = TestUtil.checkPhone(checkPhoneDTO, HttpStatus.CONFLICT);
        TestUtil.validateFailApiResponse(result);
    }

    @Order(value = 30)
    @Test
    void signUpSuccessCase() {
        SignUpDTO signUpDTO = new SignUpDTO(firstName, lastName, phoneNumber, password, password, code, smsCodeId, true);
        ApiResult<TokenDTO> result = TestUtil.signUp(signUpDTO, HttpStatus.OK);
        TestUtil.validateSuccessApiResponse(result);
        Assertions.assertThat(result.getData().getDeviceKey()).isNotNull();
    }

    @Order(value = 35)
    @Test
    public void checkPhoneSuccessForReliableDevice() {
        CheckPhoneDTO checkPhoneDTO = new CheckPhoneDTO(phoneNumber2);
        ApiResult<SmsCodeDTO> result = TestUtil.checkPhone(checkPhoneDTO, HttpStatus.OK);
        TestUtil.validateSuccessApiResponse(result);
        smsCodeId = result.getData().getSmsCodeId();
        ApiResult<String> smsCode = TestUtil.getSmsCodeBySmsCodeId(smsCodeId, TestUtil.SMS_CODE_TEST_PASSWORD, HttpStatus.OK);
        code = smsCode.getMessage();
    }

    @Order(value = 36)
    @Test
    void signUpSuccessForReliableDeviceNullCase() {
        SignUpDTO signUpDTO = new SignUpDTO(firstName, lastName, phoneNumber2, password, prePassword, code, smsCodeId, false);
        ApiResult<TokenDTO> result = TestUtil.signUp(signUpDTO, HttpStatus.OK);
        TestUtil.validateSuccessApiResponse(result);
        Assertions.assertThat(result.getData().getDeviceKey()).isNull();
    }


    // Berilgan smscode notog'ri bo'lganda xatolikni tekshiruvchi test
    @Order(value = 50)
    @Test
    void signUpFailCase() {
        SignUpDTO signUpDTO = new SignUpDTO(firstName, lastName, "+998909097425", password, prePassword, code + "1", smsCodeId, false);
        ApiResult<TokenDTO> result = TestUtil.signUp(signUpDTO, HttpStatus.BAD_REQUEST);
        TestUtil.validateFailApiResponse(result);
    }


    // Password va prePassword mos bo'lmaganda xatolikni tekshiruvchi test
    @Order(value = 60)
    @Test
    void signUpPasswordMismatchCase() {
        SignUpDTO signUpDTO = new SignUpDTO(firstName, lastName, "+998909097422", password, prePassword + 1, code, smsCodeId, false);
        ApiResult<TokenDTO> result = TestUtil.signUp(signUpDTO, HttpStatus.BAD_REQUEST);
        TestUtil.validateFailApiResponse(result);
    }

    @Order(value = 70)
    @Test
    void signInSuccessCase() {
        SignInDTO signInDTO = new SignInDTO(phoneNumber, password);
        ApiResult<SignInReturnDTO> result = TestUtil.signIn(signInDTO, HttpStatus.OK);
        TestUtil.validateSuccessApiResponse(result);
        smsCodeId = result.getData().getSms().getSmsCodeId();
        ApiResult<String> smsCode = TestUtil.getSmsCodeBySmsCodeId(smsCodeId, TestUtil.SMS_CODE_TEST_PASSWORD, HttpStatus.OK);
        code = smsCode.getMessage();
    }

    // Telefon raqami yoki password noto'g'ri bo'lganda exception berishini tekshirish uchun
    @Order(value = 80)
    @Test
    void signInFailCase() {
        SignInDTO signInDTO = new SignInDTO(phoneNumber, password + "1");
        ApiResult<SignInReturnDTO> result = TestUtil.signIn(signInDTO, HttpStatus.UNAUTHORIZED);
        TestUtil.validateFailApiResponse(result);
    }

    @Order(value = 90)
    @Test
    void signInWithSmsCodeSuccessCase() {

        SmsCodeDTO smsCodeDTO = new SmsCodeDTO(smsCodeId, code, true);

        ApiResult<TokenDTO> result = TestUtil.signInWithSmsCode(smsCodeDTO, HttpStatus.OK);
        TestUtil.validateSuccessApiResponse(result);
    }

    @Order(value = 100)
    @Test
    void forgotPasswordSuccessCase() {
        CheckPhoneDTO checkPhoneDTO = new CheckPhoneDTO(phoneNumber);
        ApiResult<SmsCodeDTO> result = TestUtil.forgotPassword(checkPhoneDTO, HttpStatus.OK);
        TestUtil.validateSuccessApiResponse(result);

        smsCodeId = result.getData().getSmsCodeId();
        ApiResult<String> smsCode = TestUtil.getSmsCodeBySmsCodeId(smsCodeId, TestUtil.SMS_CODE_TEST_PASSWORD, HttpStatus.OK);
        code = smsCode.getMessage();
    }

    //Database da yo'q bo'lgan telefon raqam kiritilganda exception berishini tekshirish
    @Order(value = 110)
    @Test
    void forgotPasswordFailCase() {
        CheckPhoneDTO checkPhoneDTO = new CheckPhoneDTO("+998908765432");
        ApiResult<SmsCodeDTO> result = TestUtil.forgotPassword(checkPhoneDTO, HttpStatus.NOT_FOUND);
        TestUtil.validateFailApiResponse(result);
    }

    @Order(value = 120)
    @Test
    void checkSmsCodeForForgotPasswordSuccessCase() {
        SmsCodeDTO smsCodeDTO = new SmsCodeDTO(smsCodeId, code, true);
        ApiResult<SmsCodeDTO> result = TestUtil.checkSmsForforgotPassword(smsCodeDTO, HttpStatus.OK);
        TestUtil.validateSuccessApiResponse(result);
    }

    @Order(value = 130)
    @Test
    void checkSmsCodeForForgotPasswordFailCase() {
        SmsCodeDTO smsCodeDTO = new SmsCodeDTO(smsCodeId, code + "2", true);
        ApiResult<SmsCodeDTO> result = TestUtil.checkSmsForforgotPassword(smsCodeDTO, HttpStatus.BAD_REQUEST);
        TestUtil.validateFailApiResponse(result);
    }

    @Order(value = 140)
    @Test
    void resetPasswordSuccessCase() {
        final ResetPasswordDTO resetPasswordDTO = ResetPasswordDTO.builder()
                .smsCode(code)
                .smsCodeId(smsCodeId)
                .reliableDevice(false)
                .password(newPassword)
                .prePassword(newPrePassword)
                .build();
        ApiResult<TokenDTO> result = TestUtil.resetPassword(resetPasswordDTO, HttpStatus.OK);
        TestUtil.validateSuccessApiResponse(result);
    }

    @Order(value = 145)
    @Test
    void resetPasswordFailCase() {
        final ResetPasswordDTO resetPasswordDTO = ResetPasswordDTO.builder()
                .smsCode(code)
                .smsCodeId(smsCodeId)
                .reliableDevice(false)
                .password(newPassword)
                .prePassword(newPrePassword)
                .build();
        ApiResult<TokenDTO> result = TestUtil.resetPassword(resetPasswordDTO, HttpStatus.BAD_REQUEST);
        TestUtil.validateFailApiResponse(result);
    }

    @Order(value = 150)
    @Test
    void resetPasswordFailPasswordMismatchCase() {
//        ResetPasswordDTO resetPasswordDTO = new ResetPasswordDTO(newPassword, newPrePassword + "!");
//        ApiResult<TokenDTO> result = TestUtil.resetPassword(resetPasswordDTO, HttpStatus.BAD_REQUEST);
//        TestUtil.validateFailApiResponse(result);
    }


}