package ar.com.huella.huella.securizer.service;

import java.util.Map;

public interface IEmailService {

    public void sendVerificationEmail(String to, String token);

    public void sendPasswordResetEmail(String to, String token);

    void sendCaseNotification(String to, String subject, String templateName, Map<String, Object> variables);
    
}
