package ai.ecma.appauth.controller;

import ai.ecma.appauth.payload.ApiResult;
import ai.ecma.appauth.payload.UserQuestionWithTwoStepPasswordDTO;
import ai.ecma.appauth.service.UserQuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserQuestionControllerImpl implements UserQuestionController {
    private final UserQuestionService questionService;

    @Override
    public ApiResult<String> addUserQuestion(@Valid @RequestBody UserQuestionWithTwoStepPasswordDTO userQuestionWithTwoStepPasswordDTO) {
        log.info("addUserQuestion method entered: {}", userQuestionWithTwoStepPasswordDTO);
        ApiResult<String> result = questionService.addUserQuestion(userQuestionWithTwoStepPasswordDTO);
        log.info("addUserQuestion method exited: {}", result);
        return result;
    }

    @Override
    public ApiResult<String> editUserQuestion(UserQuestionWithTwoStepPasswordDTO userQuestionWithTwoStepPasswordDTO) {
        log.info("editUserQuestion method entered: {}", userQuestionWithTwoStepPasswordDTO);
        ApiResult<String> result = questionService.editUserQuestion(userQuestionWithTwoStepPasswordDTO);
        log.info("editUserQuestion method exited: {}", result);
        return result;
    }

    @Override
    public ApiResult<String> removeUserQuestion(UserQuestionWithTwoStepPasswordDTO userQuestionWithTwoStepPasswordDTO) {
        log.info("removeUserQuestion method entered: {}", userQuestionWithTwoStepPasswordDTO);
        ApiResult<String> result = questionService.removeUserQuestion(userQuestionWithTwoStepPasswordDTO);
        log.info("removeUserQuestion method exited: {}", result);
        return result;
    }
}
