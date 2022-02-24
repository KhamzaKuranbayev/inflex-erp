package ai.ecma.appauth.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModuleDTO {

    @NotNull(message = "Modul tanlanishi shart")
    private Long id;

    private String title;

    private String name;

    //USERLARGA QANDAY CHAP TOMON MENUDA KETMA-KETLIKDA CHIQISHI
    private Integer orderIndex;

    @NotEmpty
    private List<DepartmentDTO> departments;
}
