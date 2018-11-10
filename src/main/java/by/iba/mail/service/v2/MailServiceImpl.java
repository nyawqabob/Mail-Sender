package by.iba.mail.service.v2;

import by.iba.mail.config.exception.AttachmentSendingException;
import by.iba.mail.config.properties.MailData;
import by.iba.mail.creater.MimeMessageHelperCreator;
import by.iba.mail.entity.MessageStructure;
import by.iba.mail.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Component
public class MailServiceImpl implements MailService {

    private MailData mailData;
    private JavaMailSender mailSender;
    private MimeMessageHelperCreator mimeMessageHelperCreator;

    @Autowired
    public MailServiceImpl(MailData mailData, JavaMailSender mailSender, MimeMessageHelperCreator mimeMessageHelper) {
        this.mailData = mailData;
        this.mailSender = mailSender;
        this.mimeMessageHelperCreator = mimeMessageHelper;
    }

    @Override
    public void sendMessage(MessageStructure messageStructure) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = mimeMessageHelperCreator.create(mimeMessage, messageStructure, mailData.getUsername());
        } catch (MessagingException e) {
            throw new AttachmentSendingException("Error while sending message. Try again later. ");
        }
        mailSender.send(mimeMessage);
    }

    @Override
    public void sendMessageWithAttachment(MessageStructure messageStructure) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = mimeMessageHelperCreator.create(mimeMessage, messageStructure, mailData.getUsername());
            FileSystemResource file = new FileSystemResource(new File("D:\\046-5х5-600х600.jpg"));
            mimeMessageHelper.addAttachment("randomFile", file);
        } catch (MessagingException e) {
            throw new AttachmentSendingException("Error while sending message. Try again later. ");
        }
        mailSender.send(mimeMessage);
    }

    @Override
    public void sendMessageWithInlineAttachment(MessageStructure messageStructure) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = mimeMessageHelperCreator.create(mimeMessage, messageStructure, mailData.getUsername());
            FileSystemResource file
                    = new FileSystemResource(new File("D:\\046-5х5-600х600.jpg"));
            mimeMessageHelper.addInline("choclicom.jpg", file);
        } catch (MessagingException e) {
            throw new AttachmentSendingException("Error while sending message. Try again later. ");
        }
        mailSender.send(mimeMessage);
    }
}

