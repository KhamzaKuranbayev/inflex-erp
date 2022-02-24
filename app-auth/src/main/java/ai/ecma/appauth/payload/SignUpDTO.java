package ai.ecma.appauth.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDTO extends SmsCodeDTO {
    @NotBlank(message = "Ism bo'sh bo'lmasin")
    private String firstName;

    @NotBlank(message = "Familya bo'sh bo'lmasin")
    private String lastName;

    @NotBlank(message = "{PHONE_NUMBER_SHOULD_NOT_BE_EMPTY}")
    @Pattern(regexp = "^[+][0-9]{9,15}$",
            message = "Telefon raqam formati xato. Telefon raqam + bilan boshlanib, 9 tadan 15 tagacha sonlarda iborat bo'lishi kerak")
    private String phoneNumber;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+=])(?=\\S+$).{8,}$", message = "Parolda kamida bitta katta harf, kamida bitta kichik harf, kamida bitta raqam va !@#$%^&*()_+= belgilardan biri ishlatilishi kerak ")
    private String password;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+=])(?=\\S+$).{8,}$", message = "Parolda kamida bitta katta harf, kamida bitta kichik harf, kamida bitta raqam va !@#$%^&*()_+= belgilardan biri ishlatilishi kerak ")
    private String prePassword;

    private String voucherCode;

    public SignUpDTO(String firstName, String lastName, String phoneNumber, String password, String prePassword,
                     @NotNull(message = "SMS bo'sh bo'lmasin") String smsCode,
                     @NotNull(message = "SMS code id maydoni bo'sh bo'lmasin") UUID smsCodeId,
                     @NotNull(message = "Ishonchli qurilma  bo'sh bo'lmasin") Boolean reliableDevice) {
        super(smsCodeId, smsCode, reliableDevice);
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.prePassword = prePassword;
        this.voucherCode = voucherCode;
    }
}
