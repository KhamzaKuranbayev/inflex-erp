package ai.ecma.appauth.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.Pattern;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class UserEditDTO {
    private UUID id;

    //ISMI
    private String firstName;

    //FAMILYASI
    private String lastName;

    //OTASINING ISMI
    private String patron;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+=])(?=\\S+$).{8,}$", message = "Parolda kamida bitta katta harf, kamida bitta kichik harf, kamida bitta raqam va !@#$%^&*()_+= belgilardan biri ishlatilishi kerak ")
    private String currentPassword;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+=])(?=\\S+$).{8,}$", message = "Parolda kamida bitta katta harf, kamida bitta kichik harf, kamida bitta raqam va !@#$%^&*()_+= belgilardan biri ishlatilishi kerak ")
    private String newPassword;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+=])(?=\\S+$).{8,}$", message = "Parolda kamida bitta katta harf, kamida bitta kichik harf, kamida bitta raqam va !@#$%^&*()_+= belgilardan biri ishlatilishi kerak ")
    private String prePassword;

    //TELEFON RAQAMI
    private String phoneNumber;

    //AVATARDAGI RASM ID SI
    private UUID photoId;

}
