package by.iba.mail.config;

import by.iba.mail.config.properties.MailData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfiguration {

    @Autowired
    private MailData mailData;

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        String host = mailData.getHost();
        String protocol = mailData.getProtocol();
        int port = mailData.getPort();
        String username = mailData.getUsername();
        String password = mailData.getPassword();
        mailSender.setHost(host);
        mailSender.setProtocol(protocol);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.transport.protocol", mailData.getProtocol());
        properties.setProperty("mail.debug", "true");
        properties.put("mail.smtp.starttls.required", "true");
        return mailSender;
    }
}
