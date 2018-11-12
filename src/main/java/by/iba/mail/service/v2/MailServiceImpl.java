package by.iba.mail.service.v2;

import by.iba.mail.config.properties.MailData;
import by.iba.mail.creator.MimeMessageHelperCreator;
import by.iba.mail.entity.MessageStructure;
import by.iba.mail.exception.MessageSendingException;
import by.iba.mail.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.net.URL;

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
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new MessageSendingException("Error while sending message. Try again later. ", e);
        }
    }

    @Override
    public void sendMessageWithAttachment(MessageStructure messageStructure) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = mimeMessageHelperCreator.createAndSet(mimeMessage, messageStructure, mailData.getUsername());
            URL url = new URL(messageStructure.getImageUrl());
            FileSystemResource file = new FileSystemResource(new File(url.getFile()));
            mimeMessageHelper.addAttachment("fotka.jpg", file);
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new MessageSendingException("Error while sending message. Try again later. ", e);
        }
    }

    @Override
    public void sendMessageWithInlineAttachment(MessageStructure messageStructure) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = mimeMessageHelperCreator.createAndSet(mimeMessage, messageStructure, mailData.getUsername());
            URL url = new URL(messageStructure.getImageUrl());
            FileSystemResource file = new FileSystemResource(new File(url.getFile()));
            mimeMessageHelper.addInline("fotka.jpg", file);
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new MessageSendingException("Error while sending message. Try again later. ", e);
        }
    }
}

