package com.rideLink.app.RideLink.services.impl;

import com.rideLink.app.RideLink.services.EmailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderService {

    private final JavaMailSender javaMailSender;

    @Override
    public void sendEmail(String toEmail, String subject, String body) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

            simpleMailMessage.setTo(toEmail); // receiver email
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(body);

            javaMailSender.send(simpleMailMessage);
            log.info("Email sent successfully to {}", toEmail);
        } catch (Exception e) {
            log.error("Cannot send email to {}: {}", toEmail, e.getMessage());
        }
    }

    @Override
    public void sendEmail(String[] toEmails, String subject, String body) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

            // Send to multiple users
            simpleMailMessage.setTo(toEmails);  // <-- FIXED (removed hardcoded email)
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(body);

            javaMailSender.send(simpleMailMessage);
            log.info("Bulk email sent successfully to {}", (Object) toEmails);
        } catch (Exception e) {
            log.error("Cannot send bulk email: {}", e.getMessage());
        }
    }
}
