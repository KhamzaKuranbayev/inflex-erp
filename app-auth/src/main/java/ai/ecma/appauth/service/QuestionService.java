package ai.ecma.appauth.service;

import ai.ecma.appauth.payload.ApiResult;
import ai.ecma.appauth.payload.QuestionDTO;
import ai.ecma.appauth.payload.RoleDTO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


public interface QuestionService {

    ApiResult<String> addQuestion(QuestionDTO questionDTO );

    ApiResult<List<QuestionDTO>> getAllQuestions();

    ApiResult<QuestionDTO> getQuestion(Long id);

    ApiResult<String> editQuestion(Long id, QuestionDTO questionDTO );

    ApiResult<String> deleteQuestion(Long id);

}
