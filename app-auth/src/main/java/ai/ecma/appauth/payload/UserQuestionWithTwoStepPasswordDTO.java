package ai.ecma.appauth.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserQuestionWithTwoStepPasswordDTO {

    //MIJOZNING TWO STEP VERIFICATION PAROL
    @NotBlank(message = "{USER_QUESTION_ANSWER_SHOULD_NOT_BE_EMPTY}")
    private String twoStepPassword;

    //XAVFISIZLIK SAVOLINING TARTIBI
    @NotNull(message = "{BAD_REQUEST}")
    @NotEmpty(message = "{BAD_REQUEST}")
    @Size(message = "{BAD_REQUEST}")
    private List<UserQuestionDTO> userQuestions;
}
