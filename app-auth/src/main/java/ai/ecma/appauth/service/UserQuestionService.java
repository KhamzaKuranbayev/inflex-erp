package ai.ecma.appauth.service;

import ai.ecma.appauth.payload.ApiResult;
import ai.ecma.appauth.payload.UserQuestionDTO;
import ai.ecma.appauth.payload.UserQuestionWithTwoStepPasswordDTO;

import java.util.List;


public interface UserQuestionService {
    ApiResult<String> addUserQuestion(UserQuestionWithTwoStepPasswordDTO userQuestionWithTwoStepPasswordDTO);

    ApiResult<String> editUserQuestion(UserQuestionWithTwoStepPasswordDTO userQuestionWithTwoStepPasswordDTO);

    ApiResult<String> removeUserQuestion(UserQuestionWithTwoStepPasswordDTO userQuestionWithTwoStepPasswordDTO);
}
