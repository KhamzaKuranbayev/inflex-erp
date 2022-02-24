package ai.ecma.appauth.controller;

import ai.ecma.appauth.payload.ApiResult;
import ai.ecma.appauth.payload.QuestionDTO;
import ai.ecma.appauth.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class QuestionControllerImpl implements QuestionController {
    private final QuestionService questionService;


    @Override
    public ApiResult<List<QuestionDTO>> getAllQuestions() {
        log.info("getAllQuestions method entered ");
        ApiResult<List<QuestionDTO>> result = questionService.getAllQuestions();
        log.info("getAllQuestions method exited: {}", result);
        return result;
    }

    @Override
    public ApiResult<QuestionDTO> getQuestion(Long id) {
        log.info("getQuestion method entered: {}", id);
        ApiResult<QuestionDTO> result = questionService.getQuestion(id);
        log.info("getQuestion method exited: {}", result);
        return result;
    }


    @Override
    public ApiResult<String> addQuestion(QuestionDTO questionDTO) {
        log.info("addQuestion method entered: {}", questionDTO);
        ApiResult<String> result = questionService.addQuestion(questionDTO);
        log.info("addQuestion method exited: {}", result);
        return result;
    }

    @Override
    public ApiResult<String> editQuestion(Long id, QuestionDTO questionDTO) {
        log.info("editQuestion method entered with id: {},{}", id, questionDTO);
        ApiResult<String> result = questionService.editQuestion(id, questionDTO);
        log.info("editQuestion method exited: {}", result);
        return result;
    }

    @Override
    public ApiResult<String> deleteQuestion(Long id) {
        log.info("deleteQuestion method entered: {}", id);
        ApiResult<String> result = questionService.deleteQuestion(id);
        log.info("deleteQuestion method exited: {}", result);
        return result;
    }
}
