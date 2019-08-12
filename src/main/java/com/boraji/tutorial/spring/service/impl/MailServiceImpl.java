package com.boraji.tutorial.spring.service.impl;

import com.boraji.tutorial.spring.entity.Code;
import com.boraji.tutorial.spring.service.MailService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class MailServiceImpl implements MailService {

    private static final Logger logger = Logger.getLogger(MailServiceImpl.class);

    public void sendMessage(Code code) {

        final String username = "tes.web.stor@gmail.com";
        final String password = "testWebStore";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(code.getUser().getEmail())
            );
            message.setSubject("Code of authentication");
            message.setText(Integer.toString(code.getCodeValue()));
            Transport.send(message);
        } catch (MessagingException e) {
            logger.error(String.format("Failed send message for user id = '%s",code.getUser().getId()), e);
        }
    }
}
