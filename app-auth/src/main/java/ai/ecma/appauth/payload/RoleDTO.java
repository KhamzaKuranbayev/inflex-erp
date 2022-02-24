package ai.ecma.appauth.payload;

import ai.ecma.appauth.entity.enums.RoleType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleDTO {

    private Long id;//EDIT,GET

    private String description;//ADD AND EDIT,GET

    @NotBlank(message = "Role nomi bo'sh bo'lmasin")
    private String name;//ADD AND EDIT,GET

    @NotNull(message = "Role turi bo'sh bo'lmasin")
    private RoleType roleType;//ADD, EDIT,GET

    private List<RoleTypeDTO> roleTypes;//GET

    @NotEmpty(message = "Modullar listi bo'sh bo'lmasin")
    private List<ModuleDTO> modules;//ADD, EDIT,GET

    private Long defaultPageId;//GET
}
