package ntcgoer.emailservice.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import ntcgoer.emailservice.configuration.EmailConfiguration;
import ntcgoer.emailservice.service.EmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {
    private EmailConfiguration emailConfiguration;
    private final TemplateEngine templateEngine;
    private JavaMailSender emailSender;

    @Override
    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(this.emailConfiguration.getFromEmail());
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        this.emailSender.send(message);
    }

    @Override
    public void sendTemplateMessage(String to, String subject, String template, Context context) throws MessagingException {
        MimeMessage mimeMessage = this.emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        String htmlContent = this.templateEngine.process(template, context);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);
        // Send the email
        this.emailSender.send(mimeMessage);
    }
}
