package ai.ecma.appauth.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

//USHBU CLASSNI MIJOZGA SMS YUBORGACH UNGA XABAR UCHUN
// VA MIJOZDAN BIZGA SMS CODE NI YUBORISHI UCHUN HAM ISHLATAMIZ
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CheckUserDTO {

    private boolean registered;

    private boolean hasPassword;
}
