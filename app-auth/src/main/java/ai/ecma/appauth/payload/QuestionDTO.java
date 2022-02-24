package ai.ecma.appauth.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDTO {

    @NotBlank(message = "Savol bo`sh bo`lmasligi kerak")
    private String question;
}
