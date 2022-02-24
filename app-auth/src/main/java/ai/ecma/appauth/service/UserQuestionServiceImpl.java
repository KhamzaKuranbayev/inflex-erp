package ai.ecma.appauth.service;

import ai.ecma.appauth.entity.User;
import ai.ecma.appauth.entity.UserQuestion;
import ai.ecma.appauth.mapper.MainMapper;
import ai.ecma.appauth.payload.ApiResult;
import ai.ecma.appauth.payload.UserQuestionWithTwoStepPasswordDTO;
import ai.ecma.appauth.repository.UserQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class UserQuestionServiceImpl implements UserQuestionService {

    private final UserQuestionRepository userQuestionRepository;
    private final MainService mainService;
    private final MainMapper mainMapper;

    @Override
    public ApiResult<String> addUserQuestion(UserQuestionWithTwoStepPasswordDTO userQuestionWithTwoStepPasswordDTO) {
        //TIZIMDAGI (SECURITY_CONTEXT DAGI) MAVJUD USERNI ANIQLAB OLAMIZ
        User currentUser = mainService.getUserFromSecurityContextIfNullThrow();

        //USERDA IKKI BOSQICHLI XAVFSIZLIK ILGARI AKTIVLASHTIRILMAGAN BO`LSA THROW
        mainService.checkTwoStepVerificationOnIfOffThrow(currentUser);

        //USERNING PAROLI TO`G`RILIGINI TEKSHIRISH
        mainService.checkUserPassword(userQuestionWithTwoStepPasswordDTO.getTwoStepPassword(), currentUser.getTwoStepPassword());

        //HAMMA NARSA TEKSHIRIB BO'LINGACH USER_QUESTION LARNI SAQLASH
        saveUserQuestionList(userQuestionWithTwoStepPasswordDTO, currentUser);

        return ApiResult.successResponse(mainService.getMessageByKey("QUESTION_SUCCESSFULLY_SAVED"));
    }

    @Override
    public ApiResult<String> editUserQuestion(UserQuestionWithTwoStepPasswordDTO userQuestionWithTwoStepPasswordDTO) {

        //TIZIMDAGI (SECURITY_CONTEXT DAGI) MAVJUD USERNI ANIQLAB OLAMIZ
        User currentUser = mainService.getUserFromSecurityContextIfNullThrow();

        //USERDA IKKI BOSQICHLI XAVFSIZLIK ILGARI AKTIVLASHTIRILMAGAN BO`LSA THROW
        mainService.checkTwoStepVerificationOnIfOffThrow(currentUser);

        //USERNING PAROLI TO`G`RILIGINI TEKSHIRISH
        mainService.checkUserPassword(userQuestionWithTwoStepPasswordDTO.getTwoStepPassword(), currentUser.getTwoStepPassword());

        //USERNI AVVALGI SAVOLLARGA BERGAN JAVOBLARINI O'CHIRYAPMIZ
        userQuestionRepository.deleteAllByUserId(currentUser.getId());

        //HAMMA NARSA TEKSHIRIB BO'LINGACH USER_QUESTION LARNI SAQLASH
        saveUserQuestionList(userQuestionWithTwoStepPasswordDTO, currentUser);

        return ApiResult.successResponse(mainService.getMessageByKey("QUESTION_SUCCESSFULLY_EDITED"));
    }

    @Override
    public ApiResult<String> removeUserQuestion(UserQuestionWithTwoStepPasswordDTO userQuestionWithTwoStepPasswordDTO) {
        //TIZIMDAGI (SECURITY_CONTEXT DAGI) MAVJUD USERNI ANIQLAB OLAMIZ
        User currentUser = mainService.getUserFromSecurityContextIfNullThrow();

        //USERDA IKKI BOSQICHLI XAVFSIZLIK ILGARI AKTIVLASHTIRILMAGAN BO`LSA THROW
        mainService.checkTwoStepVerificationOnIfOffThrow(currentUser);

        //USERNING PAROLI TO`G`RILIGINI TEKSHIRISH
        mainService.checkUserPassword(userQuestionWithTwoStepPasswordDTO.getTwoStepPassword(), currentUser.getTwoStepPassword());

        //USERNI AVVALGI SAVOLLARGA BERGAN JAVOBLARINI O'CHIRYAPMIZ
        userQuestionRepository.deleteAllByUserId(currentUser.getId());

        return ApiResult.successResponse(mainService.getMessageByKey("QUESTION_SUCCESSFULLY_DELETED"));
    }


    //HAMMA NARSA TEKSHIRIB BO'LINGACH USER_QUESTION LARNI SAQLASH
    private void saveUserQuestionList(UserQuestionWithTwoStepPasswordDTO userQuestionWithTwoStepPasswordDTO, User currentUser) {

        //USERNING SAVOLLAR KETMA-KETLIGI UCHUN O'ZGARUVCHI
        AtomicInteger orderQuestion = new AtomicInteger();

        //USER BERGAN SAVOLLAR VA UNGA JAVOBLARNI KETMA KETLIK BO'YICHA SAQLAYDI
        userQuestionWithTwoStepPasswordDTO.getUserQuestions().forEach(userQuestionDTO -> {
            userQuestionDTO.setUserId(currentUser.getId());
            userQuestionDTO.setOrder(orderQuestion.incrementAndGet());
        });

        //USER_QUESTION_DTO_LIST NI USER_QUESTION LISTIGA O'TKAZISH
        List<UserQuestion> userQuestions = mainMapper.mapToUserQuestionList(userQuestionWithTwoStepPasswordDTO.getUserQuestions());

        //USER_QUESTION NI LISTINI SAQLASH
        userQuestionRepository.saveAll(userQuestions);
    }
}

