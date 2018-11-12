package by.iba.mail.service.v2;

import by.iba.mail.exception.MessageSendingException;
import by.iba.mail.config.properties.MailData;
import by.iba.mail.creator.MimeMessageHelperCreator;
import by.iba.mail.entity.MessageStructure;
import by.iba.mail.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service("secondVersion")
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
            mimeMessageHelperCreator.createAndSet(mimeMessage, messageStructure, mailData.getUsername());
        } catch (MessagingException e) {
            throw new MessageSendingException("Error while sending message. Try again later. ", e);
        }
        mailSender.send(mimeMessage);
    }

    @Override
    public void sendMessageWithAttachment(MessageStructure messageStructure) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = mimeMessageHelperCreator.createAndSet(mimeMessage, messageStructure, mailData.getUsername());
            FileSystemResource file = new FileSystemResource(new File("D:\\046-5х5-600х600.jpg"));
            mimeMessageHelper.addAttachment("fotka.jpg", file);
        } catch (MessagingException e) {
            throw new MessageSendingException("Error while sending message. Try again later. ", e);
        }
        mailSender.send(mimeMessage);
    }

    @Override
    public void sendMessageWithInlineAttachment(MessageStructure messageStructure) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = mimeMessageHelperCreator.createAndSet(mimeMessage, messageStructure, mailData.getUsername());
            FileSystemResource file
                    = new FileSystemResource(new File("D:\\046-5х5-600х600.jpg"));
            mimeMessageHelper.addInline("fotka.jpg", file);
        } catch (MessagingException e) {
            throw new MessageSendingException("Error while sending message. Try again later. ", e);
        }
        mailSender.send(mimeMessage);
    }
}

