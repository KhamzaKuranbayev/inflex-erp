package ai.ecma.appauth.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserQuestionDTO {

    //XAVFSIZLIK SAVOLINING ID SI
    @NotNull(message = "Savolning id si bo`sh bo`lmasligi kerak")
    private Long questionId;

    //MIJOZ TOMONIDAN SAVOLGA BERILGAN JAVOB
    @NotBlank(message = "Savolning javobi bo`sh bo`lmasligi kerak")
    private String answer;

    //XAVFISIZLIK SAVOLINING TARTIBI
    private Integer order;

    //DTO DAN MAPPER ORQALI ENTITY YASASH UCHUN KERAK BO'LADI. QAYSI USERNING SAVOL-JAVOBI EKANLIGINI ANIQLASH UCHUN
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private UUID userId;
}
