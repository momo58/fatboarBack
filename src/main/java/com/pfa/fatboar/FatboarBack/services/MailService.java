package com.pfa.fatboar.FatboarBack.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserService userService;

    public void sendEmail() {
    	SimpleMailMessage message = new SimpleMailMessage();
    	message.setTo("valentinlourteau@gmail.com");
    	message.setFrom("valentinlourteau@gmail.com");
    	message.setSentDate(new Date());
    	message.setSubject("Sending your credentials");
    	message.setText("Please note your credentials thx");
    	javaMailSender.send(message);
        //userService.getUsersEmails();

    }
}
