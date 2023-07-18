package com.AYCTechnologies.yinkas_blog.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

    @Configuration
    @ConfigurationProperties
    public class EmailConfig {

        private static final int GMAIL_SMTP_PORT = 465;

        @Value("${spring.mail.host}")
        private String host;

        @Value("${spring.mail.username}")
        private String user;

        @Value("${spring.mail.password}")
        private String password;


        @Bean
        public JavaMailSender getJavaMailSender() {
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            // Set up Gmail config
            mailSender.setHost(host);
            mailSender.setPort(GMAIL_SMTP_PORT);

            // Set up email config (using udeesa email)
            mailSender.setUsername(user);
            mailSender.setPassword(password);

            Properties props = mailSender.getJavaMailProperties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            return mailSender;
        }
    }
