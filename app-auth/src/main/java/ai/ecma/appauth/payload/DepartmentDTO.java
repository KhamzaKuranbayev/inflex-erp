package ai.ecma.appauth.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DepartmentDTO {

    private Long id;

    private String title;

    private String name;

    //USERLARGA QANDAY CHAP TOMON MENUDA KETMA-KETLIKDA CHIQISHI
    private Integer orderIndex;

    @NotEmpty
    private List<AuthPageDTO> pages;
}
