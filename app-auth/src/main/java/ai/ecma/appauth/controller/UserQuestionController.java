package ai.ecma.appauth.controller;

import ai.ecma.appauth.payload.ApiResult;
import ai.ecma.appauth.payload.UserQuestionWithTwoStepPasswordDTO;
import ai.ecma.appauth.utils.RestConstants;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping(value = UserQuestionController.USER_QUESTION_CONTROLLER_PATH)
public interface UserQuestionController {

    String USER_QUESTION_CONTROLLER_PATH = RestConstants.BASE_PATH_V1 + "/user-question";


    /**
     * USER XAVFSIZLIK SAVOLLARINI O'RNATMOQCHI BO'LGANDA
     *
     * @param userQuestionWithTwoStepPasswordDTOList @RequestBody
     * @return ApiResult<String>
     */
    @PostMapping
    ApiResult<String> addUserQuestion(@Valid @RequestBody UserQuestionWithTwoStepPasswordDTO userQuestionWithTwoStepPasswordDTOList);


    /**
     * USER XAVFSIZLIK SAVOLLARINI TAHRIRLAMOQDA
     *
     * @param userQuestionWithTwoStepPasswordDTO @RequestBody
     * @return ApiResult<String>
     */
    @PutMapping
    ApiResult<String> editUserQuestion(@Valid @RequestBody UserQuestionWithTwoStepPasswordDTO userQuestionWithTwoStepPasswordDTO);


    /**
     * USER XAVFSIZLIK SAVOLLARINI O'CHIRMOQDA
     *
     * @param userQuestionWithTwoStepPasswordDTO @RequestBody
     * @return ApiResult<String>
     */
    @DeleteMapping
    ApiResult<String> removeUserQuestion(@RequestBody UserQuestionWithTwoStepPasswordDTO userQuestionWithTwoStepPasswordDTO);
}
