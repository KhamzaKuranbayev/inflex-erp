package ai.ecma.appauth.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserQuestionCheckDTO {

    //REQ, RES
    //XAVFSIZLIK SAVOLINING ID SI
    @NotNull(message = "Savolning id si bo`sh bo`lmasligi kerak")
    private Long questionId;

    //RES
    //MIJOZGA SAVOLNING MATNINI BERISH UCHUN
    private String questionText;

    //REQ
    //MIJOZ TOMONIDAN SAVOLGA BERGAN JAVOBI
    @NotBlank(message = "Savolning javobi bo`sh bo`lmasligi kerak")
    private String answer;

    //RES
    //XAVFISIZLIK SAVOLLARI TUGADI
    private Boolean finished;

    //REQ
    //QAYSI TELEFON RAQAMLI USER SO'RAYAPTI XAVFSIZLIK SAVOLINI?
    @NotBlank(message = "{PHONE_NUMBER_SHOULD_NOT_BE_EMPTY}")
    @Pattern(regexp = "^[+][0-9]{9,15}$", message = "{PHONE_NUMBER_PATTERN}")
    private String phoneNumber;

    //REQ
    //MIJOZDAN SAVOLLAR VA ULARGA BERGAN JAVOBLARINI LISTINI OLISH UCHUN
    private List<UserQuestionCheckDTO> userQuestions;


    public UserQuestionCheckDTO(Boolean finished) {
        this.finished = finished;
    }
}
