package com.survey.users.NotificationService.service.impl;

import com.survey.users.NotificationService.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.username}")
    private String from;
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public boolean sendMail(String to, String[] cc, String subject, String body) {
        try{
            MimeMessage mailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage,true);

            messageHelper.setFrom(from);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);

            String htmlContent = "<p>" + body + "</p>";

            htmlContent += "<p>Mozete im pristupiti putem linka <a href=\"http://localhost:8080\" style=\"text-decoration: underline; color: blue;\">aktivacioni_link</a>.</p>";

            messageHelper.setText(htmlContent, true); // true indicates HTML content

            javaMailSender.send(mailMessage);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

}
