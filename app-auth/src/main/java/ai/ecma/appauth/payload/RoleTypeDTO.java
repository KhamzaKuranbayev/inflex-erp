package ai.ecma.appauth.payload;

import ai.ecma.appauth.entity.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleTypeDTO {
    private String title;
    private String description;
    private RoleType name;
}
