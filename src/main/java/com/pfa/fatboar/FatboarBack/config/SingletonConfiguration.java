package com.pfa.fatboar.FatboarBack.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.pfa.fatboar.FatboarBack.services.ServiceImpl.GameScheduledSingleton;

@Configuration
public class SingletonConfiguration {
	
	@Bean
    public GameScheduledSingleton gameScheduledSingleton() {
        return new GameScheduledSingleton();
    } 

    
    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
         
        mailSender.setUsername("fatboarf2i@gmail.com");
        mailSender.setPassword("Fatboar3195&");
         
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtps");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
         
        return mailSender;
    }

}
