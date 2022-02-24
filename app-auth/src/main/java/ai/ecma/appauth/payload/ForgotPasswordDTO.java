package ai.ecma.appauth.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

//USHBU CLASSNI MIJOZGA SMS YUBORGACH UNGA XABAR UCHUN
// VA MIJOZDAN BIZGA SMS CODE NI YUBORISHI UCHUN HAM ISHLATAMIZ
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ForgotPasswordDTO {

    //RES
    //PAROLINI UNUTGAN USERNING EMAIL BORMI?
    private Boolean hasEmail;

    private String emailText;

    //RES
    //PAROLINI UNUTGAN USERNING XAVFSIZLIK SAVOLLARIGA JAVOB BERGANMI?
    private Boolean hasQuestion;

    //RES
    //PAROLINI UNUTGAN USERNING EMAIL BO'LMASA VA XAVFSIZLIK SAVOLLARIGA JAVOB BERMAGAN BO'LSA,
    // USHBU RAQAM TIZIMDA MAVJUD BO'LSA VA UNGA SMS YUBORILDIMI?
    private Boolean sentSms;

    //RES
    //MIJOZNING EMAIL BO'LMASA VA XAVFSIZLIK SAVOLLARIGA JAVOB BERMAGAN BO'LMASA, SMS YUBORILADI.
    // USHBU OBJECT YUBORILADI SMS GA TEGISHLI MA'LUMOTLAR YUBORILADI
    private SmsCodeDTO smsCode;

    //REQ
    //MIJOZ TANLOVDA EMAILGA XABAR YUBORISHNI TANLAGANDA
    private Boolean toEmail;

    //REQ
    //MIJOZ TANLOVDA PHONEGA XABAR YUBORISHNI TANLAGANDA
    private Boolean toPhone;

    //REQ
    //MIJOZNING TELEFON RAQAMI
    @NotBlank(message = "{PHONE_NUMBER_SHOULD_NOT_BE_EMPTY}")
    @Pattern(regexp = "^[+][0-9]{9,15}$", message = "{PHONE_NUMBER_PATTERN}")
    private String phoneNumber;
}
