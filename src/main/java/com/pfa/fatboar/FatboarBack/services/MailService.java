package com.pfa.fatboar.FatboarBack.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserService userService;

    public void sendEmail(String to, String subject, String text) {
    	SimpleMailMessage mail = new SimpleMailMessage();
    	mail.setTo("valentinlourteau@gmail.com");
    	mail.setFrom("fatboar.jeuconcours@gmail.com");
    	mail.setSentDate(new Date());
    	mail.setSubject(subject);
    	mail.setText(text);
    	javaMailSender.send(mail);
    }
}
