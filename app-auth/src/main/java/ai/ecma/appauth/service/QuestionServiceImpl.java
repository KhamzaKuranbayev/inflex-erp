package ai.ecma.appauth.service;

import ai.ecma.appauth.entity.Question;
import ai.ecma.appauth.exceptions.RestException;
import ai.ecma.appauth.payload.ApiResult;
import ai.ecma.appauth.payload.QuestionDTO;
import ai.ecma.appauth.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final MainService mainService;

    @Override
    public ApiResult<String> addQuestion(QuestionDTO questionDTO) {

        //SAVOLLAR TAKRORLANMASLIGI KERAK, AKS HOLDA THROW
        if (questionRepository.existsByQuestion(questionDTO.getQuestion()))
            throw RestException.restThrow(mainService.getMessageByKey("QUESTION_ALREADY_EXIST"), HttpStatus.CONFLICT);

        Question question = new Question(questionDTO.getQuestion());
        questionRepository.save(question);
        return ApiResult.successResponse(mainService.getMessageByKey("QUESTION_SUCCESSFULLY_SAVED"));
    }

    @Override
    public ApiResult<List<QuestionDTO>> getAllQuestions() {
        List<Question> allQuestions = questionRepository.findAll();

        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : allQuestions) {
            questionDTOList.add(new QuestionDTO(question.getQuestion()));
        }
        return ApiResult.successResponse(questionDTOList);
    }

    @Override
    public ApiResult<QuestionDTO> getQuestion(Long id) {
        Question question = questionRepository.findById(id).orElseThrow(() -> RestException.restThrow("Question", "id", id, mainService.getMessageByKey("QUESTION_NOT_FOUND")));
        return ApiResult.successResponse(new QuestionDTO(question.getQuestion()));
    }


    @Override
    public ApiResult<String> editQuestion(Long id, QuestionDTO questionDTO) {
        //SAVOLLAR TAKRORLANMASLIGI KERAK, AKS HOLDA THROW
        if (questionRepository.existsByQuestion(questionDTO.getQuestion()))
            throw RestException.restThrow(mainService.getMessageByKey("QUESTION_ALREADY_EXIST"), HttpStatus.CONFLICT);

        Question question = questionRepository.findById(id).orElseThrow(() -> RestException.restThrow("Question", "id", id, mainService.getMessageByKey("QUESTION_NOT_FOUND")));
        question.setQuestion(questionDTO.getQuestion());
        return ApiResult.successResponse(mainService.getMessageByKey("QUESTION_SUCCESSFULLY_EDITED"));
    }

    @Override
    public ApiResult<String> deleteQuestion(Long id) {
        questionRepository.deleteById(id);
        return ApiResult.successResponse(mainService.getMessageByKey("QUESTION_SUCCESSFULLY_DELETED"));
    }
}

