package ai.ecma.appauth.service;

public interface MailService {
    boolean sendEmailVerificationCode(String text, String email);
}
