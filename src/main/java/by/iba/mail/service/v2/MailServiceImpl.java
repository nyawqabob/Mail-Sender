//bypackage by.iba.mail.service.v2;
//
//import by.iba.mail.config.calendar.CalendarHandler;
//import by.iba.mail.config.properties.MailData;
//import by.iba.mail.creator.MimeMessageHelperCreator;
//import by.iba.mail.entity.MessageStructure;
//import by.iba.mail.exception.MessageSendingException;
//import by.iba.mail.service.MailService;
//import net.fortuna.ical4j.data.CalendarOutputter;
//import net.fortuna.ical4j.model.Calendar;
//import org.apache.commons.io.IOUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.ByteArrayResource;
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.core.io.InputStreamResource;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Service;
//
//import javax.mail.internet.MimeMessage;
//import java.io.*;
//import java.net.URL;
//
//@Service("secondVersion")
//public class MailServiceImpl implements MailService {
//
//    private MailData mailData;
//    private JavaMailSender mailSender;
//    private MimeMessageHelperCreator mimeMessageHelperCreator;
//    private CalendarHandler calendarHandler;
//
//    @Autowired
//    public MailServiceImpl(MailData mailData, JavaMailSender mailSender, MimeMessageHelperCreator mimeMessageHelperCreator, CalendarHandler calendarHandler) {
//        this.mailData = mailData;
//        this.mailSender = mailSender;
//        this.mimeMessageHelperCreator = mimeMessageHelperCreator;
//        this.calendarHandler = calendarHandler;
//    }
//
//    @Override
//    public void sendMessage(MessageStructure messageStructure) {
//        MimeMessage mimeMessage = mailSender.createMimeMessage();
//        try {
//            mimeMessageHelperCreator.createAndSet(mimeMessage, messageStructure, mailData.getUsername());
//            mailSender.send(mimeMessage);
//        } catch (Exception e) {
//            throw new MessageSendingException("Error while sending message. Try again later. ", e);
//        }
//    }
//
//    @Override
//    public void sendMessageWithAttachment(MessageStructure messageStructure) {
//        MimeMessage mimeMessage = mailSender.createMimeMessage();
//        try {
//            MimeMessageHelper mimeMessageHelper = mimeMessageHelperCreator.createAndSet(mimeMessage, messageStructure, mailData.getUsername());
//            URL url = new URL(messageStructure.getImageUrl());
//            FileSystemResource file = new FileSystemResource(new File(url.getFile()));
//            mimeMessageHelper.addAttachment("fotka.jpg", file);
//            mailSender.send(mimeMessage);
//        } catch (Exception e) {
//            throw new MessageSendingException("Error while sending message. Try again later. ", e);
//        }
//    }
//
//    @Override
//    public void sendMessageWithInline(MessageStructure messageStructure) {
//        MimeMessage mimeMessage = mailSender.createMimeMessage();
//        try {
//            MimeMessageHelper mimeMessageHelper = mimeMessageHelperCreator.createAndSet(mimeMessage, messageStructure, mailData.getUsername());
//            URL url = new URL(messageStructure.getImageUrl());
//            FileSystemResource file = new FileSystemResource(new File(url.getFile()));
//            mimeMessageHelper.addInline("fotka.jpg", file);
//            mailSender.send(mimeMessage);
//        } catch (Exception e) {
//            throw new MessageSendingException("Error while sending message. Try again later. ", e);
//        }
//    }
//
//    @Override
//    public void requestEvent(MessageStructure messageStructure) {
//
//        InputStream inputStream = null;
//        try {
//            MimeMessage mimeMessage = mailSender.createMimeMessage();
//            Calendar icsCalendar = calendarHandler.createRecurEvent();
//                    FileOutputStream fout = new FileOutputStream("mycalendar2.ics");
//            CalendarOutputter outputter = new CalendarOutputter();
//            MimeMessageHelper mimeMessageHelper = mimeMessageHelperCreator.createAndSet(mimeMessage, messageStructure, mailData.getUsername());
//            inputStream = new FileInputStream(new File("mycalendar.ics"));
//            InputStreamResource inputStreamResource = new InputStreamResource(inputStream);
//            mimeMessageHelper.addInline("file", new ByteArrayResource(IOUtils.toByteArray(inputStream)), "text/calendar");
//            mailSender.send(mimeMessage);
//        } catch (Exception e) {
//            throw new MessageSendingException("Error while sending message. Try again later. ", e);
//        } finally {
//            try {
//                inputStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }
//
//    public void deleteEvent(){}
//}

