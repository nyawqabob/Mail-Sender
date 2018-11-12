package by.iba.mail.service.v1;

import by.iba.mail.config.properties.MailData;
import by.iba.mail.creator.MimeMessageCreator;
import by.iba.mail.entity.MessageStructure;
import by.iba.mail.exception.MessageSendingException;
import by.iba.mail.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.net.URL;

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
            message = mimeMessageCreator.createAndSetMultipartMessage(mailData.getUsername(), messageStructure);
            mailSender.send(message);
        } catch (Exception e) {
            throw new MessageSendingException("Error while sending message. Try again later. ", e);
        }
    }

    @Override
    public void sendMessageWithAttachment(MessageStructure messageStructure) {
        MimeMessage message;
        try {
            MimeBodyPart textOfBody = new MimeBodyPart();
            textOfBody.setContent(messageStructure.getContent(), "text/html");
            MimeBodyPart attachmentOfBody = new MimeBodyPart();
            URL url = new URL(messageStructure.getImageUrl());
            FileDataSource source = new FileDataSource(new File(url.getFile()));
            attachmentOfBody.setDataHandler(new DataHandler(source));
            attachmentOfBody.setFileName("fotka.jpg");
            message = mimeMessageCreator.createAndSetMultipartMessage(mailData.getUsername(), messageStructure, textOfBody, attachmentOfBody);
            mailSender.send(message);
        } catch (Exception e) {
            throw new MessageSendingException("Error while sending message. Try again later. ", e);
        }

    }

    @Override
    public void sendMessageWithInlineAttachment(MessageStructure messageStructure) {
        MimeMessage message;
        try {
            MimeBodyPart textOfBody = new MimeBodyPart();
            textOfBody.setContent(messageStructure.getContent() + "<img src=\"cid:fotka\">", "text/html");
            MimeBodyPart attachmentOfBody = new MimeBodyPart();
            URL url = new URL(messageStructure.getImageUrl());
            FileDataSource source = new FileDataSource(new File(url.getFile()));
            attachmentOfBody.setDataHandler(new DataHandler(source));
            attachmentOfBody.setFileName("fotka.jpg");
            attachmentOfBody.setHeader("Content-ID", "<fotka>");
            message = mimeMessageCreator.createAndSetMultipartMessage(mailData.getUsername(), messageStructure, textOfBody, attachmentOfBody);
            mailSender.send(message);
        } catch (Exception e) {
            throw new MessageSendingException("Error while sending message. Try again later. ", e);
        }
    }
}