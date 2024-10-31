package com.piexie3.daraja_api_integration.service.email;

import com.piexie3.daraja_api_integration.models.EmailDetails;
import com.piexie3.daraja_api_integration.utils.CustomException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService implements IEmailService{
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private final String senderEmail;


    @Override
    public void sendEmailAlert(EmailDetails emailDetails) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(senderEmail);
            message.setTo(emailDetails.getRecipient());
            message.setText(emailDetails.getMessageBody());
            message.setSubject(emailDetails.getSubject());

            javaMailSender.send(message);
            System.out.println("Mail sent successfully");
        } catch (MailException e) {
            throw new CustomException(e.getMessage());
        }
    }
}
