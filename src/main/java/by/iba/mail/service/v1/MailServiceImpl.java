package by.iba.mail.service.v1;

import by.iba.mail.config.exception.MessageSendingException;
import by.iba.mail.config.properties.MailData;
import by.iba.mail.creator.MimeMessageCreator;
import by.iba.mail.entity.MessageStructure;
import by.iba.mail.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service("firstVersion")
public class MailServiceImpl implements MailService {

    private MailData mailData;
    private MimeMessageCreator mimeMessageCreator;
    private JavaMailSender mailSender;

    @Autowired
    public MailServiceImpl(MailData mailData, MimeMessageCreator mimeMessageCreator, JavaMailSender mailSender) {
        this.mailData = mailData;
        this.mimeMessageCreator = mimeMessageCreator;
        this.mailSender = mailSender;
    }


    @Override
    public void sendMessage(MessageStructure messageStructure) {
        MimeMessage message;
        try {
            message = mimeMessageCreator.createAndSetSimpleMessage(mailData.getUsername(), messageStructure);
        } catch (MessagingException e) {
            throw new MessageSendingException("Error while sending message. Try again later. ", e);
        }
        mailSender.send(message);
    }

    @Override
    public void sendMessageWithAttachment(MessageStructure messageStructure) {
        MimeMessage message;
        try {
            MimeBodyPart textOfBody = new MimeBodyPart();
            textOfBody.setContent(messageStructure.getContent(), "text/html");
            MimeBodyPart attachmentOfBody = new MimeBodyPart();
            FileDataSource source = new FileDataSource(new File("D:\\046-5х5-600х600.jpg"));
            attachmentOfBody.setDataHandler(new DataHandler(source));
            attachmentOfBody.setFileName("fotka.jpg");
            message = mimeMessageCreator.createAndSetSimpleMessage(mailData.getUsername(), messageStructure, textOfBody, attachmentOfBody);
        } catch (MessagingException e) {
            throw new MessageSendingException("Error while sending message. Try again later. ", e);
        }
        mailSender.send(message);

    }

    @Override
    public void sendMessageWithInlineAttachment(MessageStructure messageStructure) {
        MimeMessage message;
        try {
            MimeBodyPart textOfBody = new MimeBodyPart();
            textOfBody.setContent(messageStructure.getContent() + "<img src=\"cid:fotka\">", "text/html");
            MimeBodyPart attachmentOfBody = new MimeBodyPart();
            FileDataSource source = new FileDataSource(new File("D:\\046-5х5-600х600.jpg"));
            attachmentOfBody.setDataHandler(new DataHandler(source));
            attachmentOfBody.setFileName("fotka.jpg");
            attachmentOfBody.setHeader("Content-ID", "<fotka>");
            message = mimeMessageCreator.createAndSetSimpleMessage(mailData.getUsername(), messageStructure, textOfBody, attachmentOfBody);
        } catch (MessagingException e) {
            throw new MessageSendingException("Error while sending message. Try again later. ", e);
        }
        mailSender.send(message);
    }
}