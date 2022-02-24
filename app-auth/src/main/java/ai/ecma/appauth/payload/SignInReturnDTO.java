package ai.ecma.appauth.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//BU CLASS CLIENT SIGN IN QILGANDA UNGA SMS YUBORAMIZMI YOKI TOKEN QAYTARAMIZMI?
// SHUNI BERADIGAN OBJECT
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignInReturnDTO {

    //AGAR MIJOZGA SMS YUBRILGAN BO'LSA USHBU FIELD QAYTADI, QOLGAN FILDLAR QAYTAMAYDI
    private SmsCodeDTO sms;

    //AGAR MIJOZGA SMS YUBRILMASA USHBU FIELD QAYTADI, QOLGAN FILDLAR QAYTAMAYDI
    private TokenDTO token;
}
