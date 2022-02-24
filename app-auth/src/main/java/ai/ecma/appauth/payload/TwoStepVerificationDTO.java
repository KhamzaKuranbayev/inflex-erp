package ai.ecma.appauth.payload;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Data
public class TwoStepVerificationDTO {
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+=])(?=\\S+$).{8,}$", message = "Parolda kamida bitta katta harf, kamida bitta kichik harf, kamida bitta raqam va !@#$%^&*()_+= belgilardan biri ishlatilishi kerak ")
    private String password;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+=])(?=\\S+$).{8,}$", message = "Parolda kamida bitta katta harf, kamida bitta kichik harf, kamida bitta raqam va !@#$%^&*()_+= belgilardan biri ishlatilishi kerak ")
    private String prePassword;

    private String hint;

    @Email(message = "{EMAIL_SHOULD_NOT_BE_EMPTY}")
    private String email;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+=])(?=\\S+$).{8,}$", message = "Parolda kamida bitta katta harf, kamida bitta kichik harf, kamida bitta raqam va !@#$%^&*()_+= belgilardan biri ishlatilishi kerak ")
    private String oldPassword;

    private String code;

    @Pattern(regexp = "^[+][0-9]{9,15}$", message = "{PHONE_NUMBER_PATTERN}")
    private String phoneNumber;
}
