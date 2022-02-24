package ai.ecma.appauth.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ResetPasswordDTO {

    @NotBlank
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+=])(?=\\S+$).{8,}$", message = "Parolda kamida bitta katta harf, kamida bitta kichik harf, kamida bitta raqam va !@#$%^&*()_+= belgilardan biri ishlatilishi kerak ")
    private String password;

    @NotBlank
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+=])(?=\\S+$).{8,}$", message = "Parolda kamida bitta katta harf, kamida bitta kichik harf, kamida bitta raqam va !@#$%^&*()_+= belgilardan biri ishlatilishi kerak ")
    private String prePassword;

    //AGAR MIJOZ SMS ORQALI PAROLNI TIKLASA, MIJOZGA YUBORILGAN SMS CODE ID SI
    private UUID smsCodeId;

    //AGAR MIJOZ SMS ORQALI PAROLNI TIKLASA, MIJOZGA YUBORILGAN SMS CODE
    @Pattern(regexp = "^[0-9]{5,8}$", message = "{BAD_REQUEST}")
    private String smsCode;

    //MIJOZ QURILMANI ISHONCHLI DEB BELGILASHI
    private Boolean reliableDevice;

    //PAROLNI UNUTGAN USERNING TELEFON RAQAMI
    @Pattern(regexp = "^[+][0-9]{9,15}$", message = "{PHONE_NUMBER_PATTERN}")
    private String phoneNumber;

    //AGAR MIJOZ EMAIL ORQALI PAROLNI TIKLASA, EMAILGA YUBORILGAN TASDIQLASH KODI
    @Pattern(regexp = "^[0-9]{5,8}$", message = "{BAD_REQUEST}")
    private String emailCode;

    //AGAR MIJOZ XAVFSIZLIK SAVOLALRI ORQALI PAROLNI TIKLASA, EMAILGA YUBORILGAN TASDIQLASH KODI
    @Size(min = 1, max = 3)
    private List<UserQuestionDTO> userQuestions;
}
