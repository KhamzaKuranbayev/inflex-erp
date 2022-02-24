package ai.ecma.appauth.controller;

import ai.ecma.appauth.payload.*;
import ai.ecma.appauth.service.AuthService;
import ai.ecma.appauth.service.MainService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@Api(tags = "Authentication")
public class AuthControllerImpl implements AuthController {
    private final AuthService authService;
    private final MainService mainService;

    @Override
    public ApiResult<CheckUserDTO> checkUser(CheckPhoneDTO checkPhoneDTO) {
        log.info("Check phone method entered: {}", checkPhoneDTO);
        ApiResult<CheckUserDTO> result = authService.checkUser(checkPhoneDTO);
        log.info("Check phone method exited: {}", result);
        return result;
    }

    @Override
    public ApiResult<SmsCodeDTO> sendSmsForRegisterPath(CheckPhoneDTO checkPhoneDTO) {
        log.info("sendSmsForRegister method entered: {}", checkPhoneDTO);
        ApiResult<SmsCodeDTO> result = authService.sendSmsForRegisterPath(checkPhoneDTO);
        log.info("sendSmsForRegisterPath method exited: {}", result);
        return result;

    }

    @Override
    public ApiResult<String> getSmsCodeBySmsCodeId(String password, UUID id) {
        return authService.getSmsCodeBySmsCodeId(password, id);
    }

    @Override
    public ApiResult<TokenDTO> signUp(SignUpDTO signUpDTO) {
        log.info("Sign up method entered: {}", signUpDTO);
        ApiResult<TokenDTO> result = authService.signUp(signUpDTO);
        log.info("Sign up method exited: {}", result);
        return result;
    }

    @Override
    public ApiResult<?> signIn(SignInDTO signInDTO) {
        log.info("Sign up method entered: {}", signInDTO);
        ApiResult<?> result = authService.signIn(signInDTO);
        log.info("Sign up method exited: {}", result);
        return result;
    }

    @Override
    public ApiResult<TokenDTO> signInWithSmsCode(SmsCodeDTO smsCodeDTO) {
        log.info("Sign In With SMS Code method entered: {}", smsCodeDTO);
        ApiResult<TokenDTO> result = authService.signInWithSmsCode(smsCodeDTO);
        log.info("Sign In With SMS Code method exited: {}", result);
        return result;
    }

    @Override
    public ApiResult<ForgotPasswordDTO> forgotPassword(CheckPhoneDTO checkPhoneDTO) {
        log.info("forgotPassword method entered: {}", checkPhoneDTO);
        ApiResult<ForgotPasswordDTO> result = authService.forgotPassword(checkPhoneDTO);
        log.info("forgotPassword method exited: {}", result);
        return result;
    }

    @Override
    public ApiResult<ForgotPasswordDTO> sendSmsOrEmailCodeForForgotPassword(ForgotPasswordDTO forgotPasswordDTO) {
        log.info("sendSmsOrEmailCodeForForgotPassword method entered: {}", forgotPasswordDTO);
        ApiResult<ForgotPasswordDTO> result = authService.sendSmsOrEmailCodeForForgotPassword(forgotPasswordDTO);
        log.info("sendSmsOrEmailCodeForForgotPassword method exited: {}", result);
        return result;
    }

    @Override
    public ApiResult<Boolean> checkEmailCodeForForgotPassword(ResetPasswordDTO resetPasswordDTO) {
        log.info("checkEmailCodeForForgotPassword method entered: {}", resetPasswordDTO);
        ApiResult<Boolean> result = authService.checkEmailCodeForForgotPassword(resetPasswordDTO);
        log.info("checkEmailCodeForForgotPassword method exited: {}", result);
        return result;
    }

    @Override
    public ApiResult<SmsCodeDTO> checkSmsCodeForForgotPassword(SmsCodeDTO smsCodeDTO) {
        log.info("checkSmsCodeForForgotPassword method entered: {}", smsCodeDTO);
        ApiResult<SmsCodeDTO> result = authService.checkSmsCodeForForgotPassword(smsCodeDTO);
        log.info("checkSmsCodeForForgotPassword method exited: {}", result);
        return result;
    }

    @Override
    public ApiResult<TokenDTO> resetPassword(ResetPasswordDTO resetPasswordDTO) {
        log.info("resetPassword method entered: {}", resetPasswordDTO);
        ApiResult<TokenDTO> result = authService.resetPassword(resetPasswordDTO);
        log.info("resetPassword method exited: {}", result);
        return result;
    }

    @Override
    public ApiResult<UserQuestionCheckDTO> checkUserQuestionAndReturnNextIfLastFinishedTrueOrGetFirstQuestion(UserQuestionCheckDTO userQuestionCheckDTO) {
        log.info("checkUserQuestionAndReturnNextIfLastFinishedTrueOrGetFirstQuestion method entered: {}", userQuestionCheckDTO);
        ApiResult<UserQuestionCheckDTO> result = authService.checkUserQuestionAndReturnNextIfLastFinishedTrueOrGetFirstQuestion(userQuestionCheckDTO);
        log.info("checkUserQuestionAndReturnNextIfLastFinishedTrueOrGetFirstQuestion method exited: {}", result);
        return result;
    }


    @Override
    public ApiResult<String> forgotLogin(String email) {
        log.info("forgotLogin method entered: {}", email);
        ApiResult<String> result = authService.forgotLogin(email);
        log.info("forgotLogin method exited: {}", result);
        return result;
    }

    @Override
    public ApiResult<Boolean> checkTwoStepVerificationPaswordPath(SignInDTO signInDTO) {
        log.info("forgotLogin method entered: {}", signInDTO);
        mainService.checkTwoStepVerificationPassword(signInDTO.getPhoneNumber(), signInDTO.getPassword());
        log.info("forgotLogin method exited success");
        return ApiResult.successResponse();
    }

    @Override
    public ApiResult<String> forgotTwoStepVerificationPaswordPath(CheckPhoneDTO checkPhoneDTO) {
        log.info("forgotTwoStepVerificationPaswordPath method entered: {}", checkPhoneDTO);
        ApiResult<String> result = authService.forgotTwoStepVerificationPaswordPath(checkPhoneDTO);
        log.info("forgotTwoStepVerificationPaswordPath method exited: {}", result);
        return result;
    }

    @Override
    public ApiResult<Boolean> checkEmailCodeForTwoStepVerificationForgotPassword(ResetPasswordDTO resetPasswordDTO) {
        log.info("checkEmailCodeForTwoStepVerificationForgotPassword method entered: {}", resetPasswordDTO);
        ApiResult<Boolean> result = mainService.checkEmailCodeForTwoStepVerificationForgotPassword(resetPasswordDTO);
        log.info("checkEmailCodeForTwoStepVerificationForgotPassword method exited: {}", result);
        return result;
    }

    @Override
    public ApiResult<String> resetTwoStepVerificationPassword(TwoStepVerificationDTO twoStepVerificationDTO) {
        log.info("resetTwoStepVerificationPassword method entered");
        ApiResult<String> apiResult = mainService.resetTwoStepVerificationPassword(twoStepVerificationDTO);
        log.info("resetTwoStepVerificationPassword method exited");
        return apiResult;
    }

    @Override
    public ApiResult<SmsCodeDTO> checkPassword(ResetPasswordDTO resetPasswordDTO) {
        log.info("resetTwoStepVerificationPassword method entered");
        ApiResult<SmsCodeDTO> apiResult = authService.checkPassword(resetPasswordDTO);
        log.info("resetTwoStepVerificationPassword method exited");
        return apiResult;
    }

    @Override
    public ApiResult<TokenDTO> setPassword(ResetPasswordDTO resetPasswordDTO) {
        log.info("resetTwoStepVerificationPassword method entered");
        ApiResult<TokenDTO> apiResult = authService.setPassword(resetPasswordDTO);
        log.info("resetTwoStepVerificationPassword method exited");
        return apiResult;
    }


    //    @Override
//    public Boolean bo() {
//        return true;
//    }
//
//    @Override
//    public Boolean bo2(UUID id) {
////        User user = new User("Sirojiddin", "123", "1136", 1L);
////        user.setId(UUID.fromString("5b302aed-1788-4371-8b0b-92973b77cffe"));
////        StudentDTO studentDTO = new StudentDTO();
////        studentDTO.setId(UUID.fromString("5b302aed-1788-4371-8b0b-92973b77cffa"));
////        studentDTO.setLastName(user.getLastName());
////        studentDTO.setFirstName("Ahmad");
////        studentDTO.setPatron("Bla");
////        studentDTO.setPhoneNumber("1137");
////        userMapper.updateStudent(studentDTO, user);
////        System.out.println(user);
//////        smsCodeRepository.deleteById(id);
//        return true;
//    }
}
