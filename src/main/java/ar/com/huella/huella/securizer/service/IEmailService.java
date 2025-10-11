package ar.com.huella.huella.securizer.service;

public interface IEmailService {

    public void sendVerificationEmail(String to, String token);

    public void sendPasswordResetEmail(String to, String token);
}
