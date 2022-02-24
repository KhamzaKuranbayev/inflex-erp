package ai.ecma.appauth.controller;

import ai.ecma.appauth.component.aop.other.annotation.CheckPermission;
import ai.ecma.appauth.payload.ApiResult;
import ai.ecma.appauth.payload.QuestionDTO;
import ai.ecma.appauth.utils.RestConstants;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static ai.ecma.appauth.entity.enums.AuthPermissionEnum.*;

@RequestMapping(value = QuestionController.QUESTION_CONTROLLER_PATH)
public interface QuestionController {
    String QUESTION_CONTROLLER_PATH = RestConstants.BASE_PATH_V1 + "/question";

    //SISTEMA EGASI XAVFSIZLIK SAVOLINI QO`SHISHI
    @CheckPermission(permissions = {ADD_QUESTION})
    @PostMapping
    ApiResult<String> addQuestion(@Valid @RequestBody QuestionDTO questionDTO);

    //XAVFSIZLIK SAVOLLARINI OLISH
    @CheckPermission(permissions = {GET_QUESTION})
    @GetMapping
    ApiResult<List<QuestionDTO>> getAllQuestions();

    //ID BO`YICHA SAVOLNI OLISH
    @CheckPermission(permissions = {GET_QUESTION})
    @GetMapping("/{id}")
    ApiResult<QuestionDTO> getQuestion(@PathVariable Long id);

    //SISTEMA EGASI XAVFSIZLIK SAVOLINI O`ZGARTIRISHI
    @CheckPermission(permissions = {EDIT_QUESTION})
    @PutMapping("/{id}")
    ApiResult<String> editQuestion(@PathVariable Long id, @Valid @RequestBody QuestionDTO questionDTO);

    //XAVFSIZLIK SAVOLINI O`CHIRISH
    @CheckPermission(permissions = {DELETE_QUESTION})
    @DeleteMapping("/{id}")
    ApiResult<String> deleteQuestion(@PathVariable Long id);
}
