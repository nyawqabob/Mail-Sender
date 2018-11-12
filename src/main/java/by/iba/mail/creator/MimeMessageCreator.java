package by.iba.mail.creator;

import by.iba.mail.entity.MessageStructure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

@Component
public class MimeMessageCreator {

    @Autowired
    private JavaMailSender mailSender;

    public MimeMessage createAndSetMultipartMessage(String username, MessageStructure messageStructure) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        setCommonParts(message, username, messageStructure);
        message.setContent(messageStructure.getContent(), "text/html");
        return message;
    }

    public MimeMessage createAndSetMultipartMessage(String username, MessageStructure messageStructure, MimeBodyPart textOfBody, MimeBodyPart attachmentOfBody) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        setCommonParts(message, username, messageStructure);
        MimeMultipart multipart = new MimeMultipart();
        multipart.addBodyPart(textOfBody);
        multipart.addBodyPart(attachmentOfBody);
        message.setContent(multipart, "text/html");
        return message;
    }

    private void setCommonParts(MimeMessage message, String username, MessageStructure messageStructure) throws MessagingException {
        message.setFrom(new InternetAddress(username));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(messageStructure.getTo()));
        message.setSubject(messageStructure.getSubject());

    }
}
