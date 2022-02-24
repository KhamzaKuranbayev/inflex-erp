package ai.ecma.academic.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @NotNull(message = "Uxalamang")
    private UUID id;

    private String lastName;

    private String firstName;

    private String patron;

    private String phoneNumber;

    private Integer tgId;

    private String telegramNumber;

    private String position;
}
