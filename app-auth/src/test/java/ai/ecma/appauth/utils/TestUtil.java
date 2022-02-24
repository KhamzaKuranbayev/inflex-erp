package ai.ecma.appauth.utils;


import ai.ecma.appauth.controller.AuthController;
import ai.ecma.appauth.payload.*;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import lombok.experimental.UtilityClass;
import org.assertj.core.api.Assertions;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@UtilityClass
public class TestUtil {

    public final String AUTH_CONTROLLER_PATH = AuthController.AUTH_CONTROLLER_PATH;

    public final Random RANDOM = new Random();
    public final String SMS_CODE_TEST_PASSWORD = UUID.randomUUID().toString();

//    private final ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
//    private final ArgumentCaptor<String> hostArgumentCaptor = ArgumentCaptor.forClass(String.class);
//    private final ArgumentCaptor<String> tokenArgumentCaptor = ArgumentCaptor.forClass(String.class);


//    public void mockSecurityContext(TokenProvider tokenProvider, String token) {
//        //Mock SecurityContext, temporary solution, need clarify why JWTFilter is not calling in tests
//        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
//
//        Authentication authentication = tokenProvider.getAuthentication(token);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
//    }

//    public ApiResult<JWTToken> activateUser(String activationToken, String password) {
//
//        ResetPasswordDTO dto = new ResetPasswordDTO();
//        dto.setKey(activationToken);
//        dto.setPassword(password);
//
//        return TestUtil.postRequest(dto, TestUtil.AUTH_CONTROLLER_PATH + AuthController.ACTIVATE, new TypeRef<ApiResult<JWTToken>>() {
//        }, null, HttpStatus.OK);


        /*RestAssuredMockMvc.standaloneSetup(pagesController);
        RestAssuredMockMvc
                .given()
                .param("token", URLDecoder.decode(verificationToken, StandardCharsets.UTF_8))
                .log().all()
                .when()
                .get(PagesController.VERIFY)
                .then()
                .log().all()
                .statusCode(200);*/
//    }


//    public ApiResult<UserLoginDTO> registerVerifyLogin(String email, String password, MailService mailService) {
//        //SIGN UP
//        ApiResult<UserLoginDTO> apiResult = registerUser(email, password, "Firstname", "Lastname", HttpStatus.OK);
//        validateSuccessApiResponse(apiResult);
////        Assertions.assertThat(apiResult.getData().getMessage()).isEqualTo("Please check your email to verify account");
//
//        //VERIFY/ACTIVATE EMAIL/ACCOUNT
//        Mockito.verify(mailService, Mockito.times(1))
//                .sendActivationEmail(userArgumentCaptor.capture(), tokenArgumentCaptor.capture());
//        String activationToken = userArgumentCaptor.getValue().getActivationKey();
//        log.debug("Activation token:{}", activationToken);
//        ApiResult<JWTToken> activationResult = activateUser(activationToken, password);
//        validateSuccessApiResponse(activationResult);
//
//        //SIGN IN/LOGIN
//        return TestUtil.signIn(email, password);
//    }

    public <T> List<T> getAndValidateNonEmptyList(ApiResult<List<T>> apiResult) {
        validateSuccessApiResponseWithNotNullData(apiResult);
        Assertions.assertThat(apiResult.getData()).isNotEmpty();
        return apiResult.getData();
    }

    public <T> void validateSuccessApiResponse(ApiResult<T> apiResult) {
        Assertions.assertThat(apiResult.getSuccess()).isTrue();
        Assertions.assertThat(apiResult.getErrors()).isNull();
    }

    public <T> void validateFailApiResponse(ApiResult<T> apiResult) {
        Assertions.assertThat(apiResult.getSuccess()).isFalse();
        Assertions.assertThat(apiResult.getErrors()).isNotEmpty();
        Assertions.assertThat(apiResult.getErrors()).isNotNull();
        Assertions.assertThat(apiResult.getData()).isNull();
    }

    public <T> void validateSuccessApiResponseWithNotNullData(ApiResult<T> apiResult) {
        Assertions.assertThat(apiResult.getSuccess()).isTrue();
        Assertions.assertThat(apiResult.getErrors()).isNull();
        Assertions.assertThat(apiResult.getData()).isNotNull();
    }

    public ApiResult<SmsCodeDTO> checkPhone(CheckPhoneDTO checkPhoneDTO, HttpStatus httpStatus) {
        return TestUtil.postRequest(checkPhoneDTO,
                AuthController.AUTH_CONTROLLER_PATH + AuthController.CHECK_PHONE_PATH,
                new TypeRef<>() {
                }, null, httpStatus);
    }


    public ApiResult<String> getSmsCodeBySmsCodeId(UUID smsCodeId, String sms_code_test_password, HttpStatus status) {
        return TestUtil.getRequest(
                AuthController.AUTH_CONTROLLER_PATH + AuthController.GET_SMS_CODE_BY_ID + "?password=" + sms_code_test_password + "&id=" + smsCodeId,
                new TypeRef<>() {
                }, null, status);
    }


    public static ApiResult<TokenDTO> signUp(SignUpDTO signUpDTO, HttpStatus httpStatus) {
        return TestUtil.postRequest(signUpDTO,
                AuthController.AUTH_CONTROLLER_PATH + AuthController.SIGN_UP_PATH,
                new TypeRef<>() {
                }, null, httpStatus);
    }

    public static ApiResult<SignInReturnDTO> signIn(SignInDTO signInDTO, HttpStatus httpStatus) {
        return TestUtil.postRequest(signInDTO,
                AuthController.AUTH_CONTROLLER_PATH + AuthController.SIGN_IN_PATH,
                new TypeRef<>() {
                }, null, httpStatus);
    }

    public static ApiResult<TokenDTO> signInWithSmsCode(SmsCodeDTO smsCodeDTO, HttpStatus httpStatus) {
        return TestUtil.postRequest(smsCodeDTO,
                AuthController.AUTH_CONTROLLER_PATH + AuthController.CHECK_SMS_CODE_FOR_SIGN_IN_PATH,
                new TypeRef<>() {
                }, null, httpStatus);
    }

    public static ApiResult<SmsCodeDTO> forgotPassword(CheckPhoneDTO checkPhoneDTO, HttpStatus httpStatus) {
        return TestUtil.postRequest(checkPhoneDTO,
                AuthController.AUTH_CONTROLLER_PATH + AuthController.FORGOT_PASWORD_PATH,
                new TypeRef<>() {
                }, null, httpStatus);
    }

    public static ApiResult<SmsCodeDTO> checkSmsForforgotPassword(SmsCodeDTO smsCodeDTO, HttpStatus httpStatus) {
        return TestUtil.postRequest(smsCodeDTO,
                AuthController.AUTH_CONTROLLER_PATH + AuthController.CHECK_SMS_CODE_FOR_FORGOT_PASSWORD_PATH,
                new TypeRef<>() {
                }, null, httpStatus);
    }

    public static ApiResult<TokenDTO> resetPassword(ResetPasswordDTO passwordDTO, HttpStatus httpStatus) {
        return TestUtil.postRequest(passwordDTO,
                AuthController.AUTH_CONTROLLER_PATH + AuthController.RESET_PASSWORD_PATH,
                new TypeRef<>() {
                }, null, httpStatus);
    }


//    public <T> byte[] getRequestForPdf(String url, String token, HttpStatus httpStatus) {
//        return RestAssuredMockMvc
//                .given()
//                .header(RestConstants.AUTHENTICATION_HEADER, token == null ? "" : token)
//                .contentType(ContentType.JSON)
//                .accept(MediaType.APPLICATION_PDF_VALUE)
//                .log().all()
//                .when()
//                .get(url)
//                .then()
//                .log().all()
//                .statusCode(httpStatus.value())
//                .contentType(MediaType.APPLICATION_PDF_VALUE)
//                .extract()
//                .body()
//                .asByteArray();
//    }


    public <T> T postRequest(Object object,
                             String postUrl,
                             TypeRef<T> typeRef,
                             String token,
                             HttpStatus httpStatus) {
        token = "Bearer " + token;
        return RestAssuredMockMvc
                .given()
                .header(RestConstants.AUTHENTICATION_HEADER, token)
                .header(RestConstants.LANGUAGE_HEADER, "en")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(object)
                .log().all()
                .when()
                .post(postUrl)
                .then()
                .log().all()
                .statusCode(httpStatus.value())
                .contentType(ContentType.JSON)
                .extract()
                .body()
                .as(typeRef);
    }


    //
    public <T> T putRequest(Object body, String putUrl, TypeRef<T> typeRef, String token, HttpStatus httpStatus) {
        token = "Bearer " + token;
        return RestAssuredMockMvc
                .given()
                .header(RestConstants.AUTHENTICATION_HEADER, token)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(body)
                .log().all()
                .when()
                .put(putUrl)
                .then()
                .log().all()
                .statusCode(httpStatus.value())
                .contentType(ContentType.JSON)
                .extract()
                .body()
                .as(typeRef);
    }


    public <T> T deleteRequest(String deleteUrl, TypeRef<T> typeRef, String token, HttpStatus httpStatus) {
        token = "Bearer " + token;
        return RestAssuredMockMvc
                .given()
                .header(RestConstants.AUTHENTICATION_HEADER, token)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .log().all()
                .when()
                .delete(deleteUrl)
                .then()
                .log().all()
                .statusCode(httpStatus.value())
                .contentType(ContentType.JSON)
                .extract()
                .body()
                .as(typeRef);
    }

    public <T> T getRequest(String url, TypeRef<T> typeRef, String token, HttpStatus httpStatus) {
        return RestAssuredMockMvc
                .given()
                .header(RestConstants.AUTHENTICATION_HEADER, token == null ? "" : token)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .log().all()
                .when()
                .get(url)
                .then()
                .log().all()
                .statusCode(httpStatus.value())
                .contentType(ContentType.JSON)
                .extract()
                .body()
                .as(typeRef);
    }


    public String withBearer(String token) {
        return "Bearer " + token;
    }





    /*public static Map<String, Integer> getStatData() {
        return getRequest(MonitoringController.MANAGEMENT + MonitoringController.AVG_BY_KEY, new TypeRef<>() {
        }, null, HttpStatus.OK);
    }*/

//    public <T> T postRequestWithLanguageHeader(Object object,
//                                               String postUrl,
//                                               TypeRef<T> typeRef,
//                                               String token,
//                                               HttpStatus httpStatus) {
//        return RestAssuredMockMvc
//                .given()
//                .header(RestConstants.AUTHENTICATION_HEADER, token == null ? "" : token)
//                .header(RestConstants.LANGUAGE_HEADER, "en")
//                .contentType(ContentType.JSON)
//                .accept(ContentType.JSON)
//                .body(object)
//                .log().all()
//                .when()
//                .post(postUrl)
//                .then()
//                .log().all()
//                .statusCode(httpStatus.value())
//                .contentType(ContentType.JSON)
//                .extract()
//                .body()
//                .as(typeRef);
//    }

}
