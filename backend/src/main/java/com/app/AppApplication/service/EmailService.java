package com.app.AppApplication.service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mauMailSender){
        this.mailSender = mauMailSender;
    }

    /**
     * @param to Recipent email
     * @param subject Email subject
     * @param text Email body
     */
    public void sendEmail(String to, String Subject, String text){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(to);
        msg.setSubject(Subject);
        msg.setText(text);
        mailSender.send(msg);
    }

    /** 
     * send a password reset email 
    */
    public void sendPassWordResetEmail(String userName, String to, String tempPassword){
        String subject = "Password Reset";
        String text = String.format(
                "Hello %s,\n\nYour password has been reset by an admin.\nTemporary Password: %s\n\nPlease log in and change your password.",
                userName, tempPassword
        );
        sendEmail(to, subject, text);
    }
}
