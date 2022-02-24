package ai.ecma.appauth.payload;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthPageDTO {

    private Long id;

    private String title;

    private String name;

    private List<PermissionDTO> permissions;

    //USHBU ROLE NI OLGAN USER TIZIMGA LOGIN YOKI REGISTER
    // QILGANDA USHBU NOMLIK SAHIFA OCILADI
    private Boolean defaultPage;

}
