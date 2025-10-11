package ar.com.huella.huella.securizer.service.implementation;

import ar.com.huella.huella.securizer.service.IEmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class EmailService implements IEmailService {

    private final JavaMailSender mailSender;
    @Value("${app.frontend.url}")
    private String frontendUrl;
    @Value("${spring.mail.username}")
    private String from;
    private final TemplateEngine templateEngine;

    private void sendEmail(String to, String subject, String htmlBody) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true); // true indica que es HTML
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Error enviando el correo a " + to, e);
        }
    }

    @Override
    public void sendVerificationEmail(String to, String token) {
        String verificationUrl = frontendUrl + "/auth/verify?token=" + token;

        Context context = new Context();
        context.setVariable("verificationUrl", verificationUrl);

        String htmlMessage = templateEngine.process("verify-account", context);

        sendEmail(to, "Verifica tu cuenta", htmlMessage);
    }

    @Override
    public void sendPasswordResetEmail(String to, String token) {
        String resetUrl = frontendUrl + "/auth/reset-password?token=" + token;

        Context context = new Context();
        context.setVariable("resetUrl", resetUrl);

        String htmlMessage = templateEngine.process("reset-password", context);

        sendEmail(to, "Restablece tu contraseña", htmlMessage);
    }

}
