package ai.ecma.appauth.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInDTO {

    @Pattern(regexp = "^[+][0-9]{9,15}$",
            message = "Telefon raqam formati xato. Telefon raqam + bilan boshlanib, 9 tadan 15 tagacha sonlarda iborat bo'lishi kerak")
    private String phoneNumber;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+=])(?=\\S+$).{8,}$", message = "Parolda kamida bitta katta harf, kamida bitta kichik harf, kamida bitta raqam va !@#$%^&*()_+= belgilardan biri ishlatilishi kerak ")
    private String password;


    //BIZGA BROWSERDA TURGAN DEVICE KEY KELADI
    private UUID deviceKey;

    public SignInDTO(String phoneNumber, String password) {
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
}
