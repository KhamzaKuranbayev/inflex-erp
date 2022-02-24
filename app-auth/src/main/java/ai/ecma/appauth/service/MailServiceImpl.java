package ai.ecma.appauth.service;

import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    @Override
    public boolean sendEmailVerificationCode(String text, String email) {
        return false;
    }
}
