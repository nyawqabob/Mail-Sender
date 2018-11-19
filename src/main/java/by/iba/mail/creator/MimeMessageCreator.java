package by.iba.mail.creator;

import by.iba.mail.config.handler.AddressSplitterator;
import by.iba.mail.entity.MessageStructure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.List;

@Component
public class MimeMessageCreator {

    private JavaMailSender mailSender;
    private AddressSplitterator addressSplitterator;

    @Autowired
    public MimeMessageCreator(JavaMailSender mailSender, AddressSplitterator addressSplitterator) {
        this.mailSender = mailSender;
        this.addressSplitterator = addressSplitterator;
    }

    public MimeMessage createAndSetMultipartMessage(String username, MessageStructure messageStructure) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        setCommonParts(message, username, messageStructure);
        message.setContent(messageStructure.getContent(), "text/calendar");
        return message;
    }

    public MimeMessage createAndSetMultipartMessage(String username, MessageStructure messageStructure, MimeBodyPart textOfBody, MimeBodyPart attachmentOfBody) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        setCommonParts(message, username, messageStructure);
        MimeMultipart multipart = new MimeMultipart();
        multipart.addBodyPart(attachmentOfBody);
        message.setContent(multipart, "text/calendar");
        return message;
    }

    private void setCommonParts(MimeMessage message, String username, MessageStructure messageStructure) throws MessagingException {
        message.setFrom(new InternetAddress(username));
        List<Address> internetAddresses = addressSplitterator.getAdressesFromString(messageStructure.getTo());
        message.addRecipients(Message.RecipientType.TO, internetAddresses.toArray(new Address[internetAddresses.size()]));
        message.setSubject(messageStructure.getSubject());

    }
}
