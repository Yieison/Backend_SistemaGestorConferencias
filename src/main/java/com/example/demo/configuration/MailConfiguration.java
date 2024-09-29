package com.example.demo.configuration;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfiguration {
	
	@Autowired
	private Environment env;

	@Bean
	public JavaMailSender getJavaMailSender() {
	    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	    mailSender.setHost("smtp.gmail.com");
	    mailSender.setPort(587);
	    String passwordCorreo = "unubbemsqgppghjg";
	    mailSender.setUsername("sistemagestionconferenciasufps@gmail.com");
	    mailSender.setPassword("krqywhmghciwirko");
	    
	    Properties props = mailSender.getJavaMailProperties();
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.debug", "true");
	    
	 // Configuraciones adicionales para SSL
	    props.put("mail.smtp.ssl.trust", "*");
	    props.put("mail.smtp.ssl.checkserveridentity", "false");
	    props.put("mail.smtp.ssl.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	   
	    
	    return mailSender;
	}

}
