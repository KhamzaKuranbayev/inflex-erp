package ai.ecma.appauth.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffDTO {

    private UUID id;

    @NotBlank(message = "Ism bo'sh bo'lmasin")
    private String firstName;

    @NotBlank(message = "Familya bo'sh bo'lmasin")
    private String lastName;

    private String patron;

    @NotBlank(message = "{PHONE_NUMBER_SHOULD_NOT_BE_EMPTY}")
    @Pattern(regexp = "^[+][0-9]{9,15}$", message = "{PHONE_NUMBER_PATTERN}")
    private String phoneNumber;

    @NotNull(message = "Role listi bo'sh bo'lmasin")
    @NotEmpty(message = "Xodimning roli bo'sh bo'lmasin")
    private List<Long> rolesId;

}
