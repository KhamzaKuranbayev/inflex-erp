package ai.ecma.appauth.service;

import ai.ecma.appauth.payload.*;

import java.util.UUID;

public interface AuthService {

    ApiResult<CheckUserDTO> checkUser(CheckPhoneDTO checkPhoneDTO);

    ApiResult<SmsCodeDTO> sendSmsForRegisterPath(CheckPhoneDTO checkPhoneDTO);

    ApiResult<TokenDTO> signUp(SignUpDTO signUpDTO);

    ApiResult<?> signIn(SignInDTO signInDTO);

    ApiResult<TokenDTO> signInWithSmsCode(SmsCodeDTO smsCodeDTO);

    ApiResult<ForgotPasswordDTO> forgotPassword(CheckPhoneDTO checkPhoneDTO);

    ApiResult<SmsCodeDTO> checkSmsCodeForForgotPassword(SmsCodeDTO smsCodeDTO);

    ApiResult<Boolean> checkEmailCodeForForgotPassword(ResetPasswordDTO resetPasswordDTO);

    ApiResult<ForgotPasswordDTO> sendSmsOrEmailCodeForForgotPassword(ForgotPasswordDTO forgotPasswordDTO);

    ApiResult<TokenDTO> resetPassword(ResetPasswordDTO resetPasswordDTO);

    ApiResult<String> getSmsCodeBySmsCodeId(String password, UUID id);

    ApiResult<UserQuestionCheckDTO> checkUserQuestionAndReturnNextIfLastFinishedTrueOrGetFirstQuestion(UserQuestionCheckDTO userQuestionCheckDTO);

    ApiResult<String> forgotLogin(String email);

    ApiResult<String> forgotTwoStepVerificationPaswordPath(CheckPhoneDTO checkPhoneDTO);

    ApiResult<SmsCodeDTO> checkPassword(ResetPasswordDTO resetPasswordDTO);

    ApiResult<TokenDTO> setPassword(ResetPasswordDTO resetPasswordDTO);
}
