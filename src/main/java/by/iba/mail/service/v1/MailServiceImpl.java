package by.iba.mail.service.v1;

import by.iba.mail.config.calendar.CalendarHandler;
import by.iba.mail.config.properties.MailData;
import by.iba.mail.creator.MimeMessageCreator;
import by.iba.mail.entity.MessageStructure;
import by.iba.mail.exception.MessageSendingException;
import by.iba.mail.service.MailService;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;

@Service("firstVersion")
public class MailServiceImpl implements MailService {

    private static final Logger LOGGER = LogManager.getLogger(MailServiceImpl.class);

    private MailData mailData;
    private MimeMessageCreator mimeMessageCreator;
    private JavaMailSender mailSender;
    private CalendarHandler calendarHandler;

    @Autowired
    public MailServiceImpl(MailData mailData, MimeMessageCreator mimeMessageCreator, JavaMailSender mailSender, CalendarHandler calendarHandler) {
        this.mailData = mailData;
        this.mimeMessageCreator = mimeMessageCreator;
        this.mailSender = mailSender;
        this.calendarHandler = calendarHandler;
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
            textOfBody.setContent(messageStructure.getContent(), "text/calendar");
            MimeBodyPart attachmentOfBody = new MimeBodyPart();
            URL url = new URL(messageStructure.getImageUrl());
            FileDataSource source = new FileDataSource(new File(url.getFile()));
            attachmentOfBody.setDataHandler(new DataHandler(source));
            attachmentOfBody.setFileName("fotka.jpg");
            //message = mimeMessageCreator.createAndSetMultipartMessage(mailData.getUsername(), messageStructure, textOfBody, attachmentOfBody);
            // mailSender.send(message);
        } catch (Exception e) {
            throw new MessageSendingException("Error while sending message. Try again later. ", e);
        }

    }

    @Override
    public void sendMessageWithInline(MessageStructure messageStructure) {
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
            // message = mimeMessageCreator.createAndSetMultipartMessage(mailData.getUsername(), messageStructure, textOfBody, attachmentOfBody);
            // mailSender.send(message);
        } catch (Exception e) {
            throw new MessageSendingException("Error while sending message. Try again later. ", e);
        }
    }

    @Override
    public void requestEvent(MessageStructure messageStructure) {
        MimeMessage message;
        try {
            MimeBodyPart textOfBody = new MimeBodyPart();
            MimeBodyPart attachmentOfBody = new MimeBodyPart();
            Calendar icsCalendar = calendarHandler.createSimpleEvent();
            CalendarOutputter calendarOutputter = new CalendarOutputter();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            calendarOutputter.output(icsCalendar, byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            DataSource source = new ByteArrayDataSource(bytes, "text/calendar");
            attachmentOfBody.setDataHandler(new DataHandler(source));
            attachmentOfBody.setFileName("calendar");
            attachmentOfBody.setHeader("Content-Class", "urn:content-  classes:calendarmessage");
            attachmentOfBody.setHeader("Content-Disposition", "inline");
            attachmentOfBody.setHeader("Content-ID", "<calendar>");
            attachmentOfBody.setHeader("Content-Type", "text/calendar; charset=utf-8; method=REQUEST");
            message = mimeMessageCreator.createAndSetMultipartMessage(mailData.getUsername(), messageStructure, textOfBody, attachmentOfBody);
            mailSender.send(message);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new MessageSendingException("Error while sending message. Try again later. ", e);
        }

    }

    @Override
    public void cancelEvent(MessageStructure messageStructure) {
        MimeMessage message;
        try {
            MimeBodyPart textOfBody = new MimeBodyPart();
            MimeBodyPart attachmentOfBody = new MimeBodyPart();
            Calendar icsCalendar = calendarHandler.cancelEvent();
            CalendarOutputter calendarOutputter = new CalendarOutputter();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            calendarOutputter.output(icsCalendar, byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            DataSource source = new ByteArrayDataSource(bytes, "text/calendar");
            attachmentOfBody.setDataHandler(new DataHandler(source));
            attachmentOfBody.setFileName("calendar");
            attachmentOfBody.setHeader("Content-Class", "urn:content-  classes:calendarmessage");
            attachmentOfBody.setHeader("Content-Disposition", "inline");
            attachmentOfBody.setHeader("Content-ID", "<calendar>");
            attachmentOfBody.setHeader("Content-Type", "text/calendar; charset=utf-8; method=CANCEL");
            message = mimeMessageCreator.createAndSetMultipartMessage(mailData.getUsername(), messageStructure, textOfBody, attachmentOfBody);
            mailSender.send(message);
        } catch (MessagingException | IOException e) {
            LOGGER.error(e.getMessage());
            throw new MessageSendingException("Error while sending message. Try again later. ", e);
        }
    }

    @Override
    public void rescheduleEvent(MessageStructure messageStructure) {
        MimeMessage message;
        try {
            MimeBodyPart textOfBody = new MimeBodyPart();
            MimeBodyPart attachmentOfBody = new MimeBodyPart();
            Calendar icsCalendar = calendarHandler.rescheduleEvent();
            CalendarOutputter calendarOutputter = new CalendarOutputter();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            calendarOutputter.output(icsCalendar, byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            DataSource source = new ByteArrayDataSource(bytes, "text/calendar");
            attachmentOfBody.setDataHandler(new DataHandler(source));
            attachmentOfBody.setFileName("calendar");
            attachmentOfBody.setHeader("Content-Class", "urn:content-  classes:calendarmessage");
            attachmentOfBody.setHeader("Content-Disposition", "inline");
            attachmentOfBody.setHeader("Content-ID", "<calendar");
            attachmentOfBody.setHeader("Content-Type", "text/calendar; charset=utf-8; method=REQUEST");
            message = mimeMessageCreator.createAndSetMultipartMessage(mailData.getUsername(), messageStructure, textOfBody, attachmentOfBody);
            mailSender.send(message);
        } catch (MessagingException | IOException e) {
            LOGGER.error(e.getMessage());
            throw new MessageSendingException("Error while sending message. Try again later. ", e);
        }
    }

    @Override
    public void updateEvent(MessageStructure messageStructure) {
        MimeMessage message;
        try {
            MimeBodyPart textOfBody = new MimeBodyPart();
            MimeBodyPart attachmentOfBody = new MimeBodyPart();
            Calendar icsCalendar = calendarHandler.updateSimpleEvent();
            CalendarOutputter calendarOutputter = new CalendarOutputter();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            calendarOutputter.output(icsCalendar, byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            DataSource source = new ByteArrayDataSource(bytes, "text/calendar");
            attachmentOfBody.setDataHandler(new DataHandler(source));
            attachmentOfBody.setFileName("calendar");
            attachmentOfBody.setHeader("Content-Class", "urn:content-  classes:calendarmessage");
            attachmentOfBody.setHeader("Content-Disposition", "inline");
            attachmentOfBody.setHeader("Content-ID", "<calendar");
            attachmentOfBody.setHeader("Content-Type", "text/calendar; charset=utf-8; method=REQUEST");
            message = mimeMessageCreator.createAndSetMultipartMessage(mailData.getUsername(), messageStructure, textOfBody, attachmentOfBody);
            mailSender.send(message);
        } catch (MessagingException | IOException e) {
            LOGGER.error(e.getMessage());
            throw new MessageSendingException("Error while sending message. Try again later. ", e);
        }
    }


    @Override
    public void addRecurEvent(MessageStructure messageStructure) {
        MimeMessage message;
        try {
            MimeBodyPart textOfBody = new MimeBodyPart();
            MimeBodyPart attachmentOfBody = new MimeBodyPart();
            Calendar icsCalendar = calendarHandler.createRecurEvent();
            CalendarOutputter calendarOutputter = new CalendarOutputter();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            calendarOutputter.output(icsCalendar, byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            DataSource source = new ByteArrayDataSource(bytes, "text/calendar");
            attachmentOfBody.setDataHandler(new DataHandler(source));
            attachmentOfBody.setFileName("calendar");
            attachmentOfBody.setHeader("Content-Class", "urn:content-  classes:calendarmessage");
            attachmentOfBody.setHeader("Content-Disposition", "inline");
            attachmentOfBody.setHeader("Content-ID", "<calendar");
            attachmentOfBody.setHeader("Content-Type", "text/calendar; charset=utf-8; method=REQUEST");
            message = mimeMessageCreator.createAndSetMultipartMessage(mailData.getUsername(), messageStructure, textOfBody, attachmentOfBody);
            mailSender.send(message);
        } catch (MessagingException | IOException e) {
            LOGGER.error(e.getMessage());
            throw new MessageSendingException("Error while sending message. Try again later. ", e);
        }
    }

    @Override
    public void cancelRecurEvent(MessageStructure messageStructure) {
        MimeMessage message;
        try {
            MimeBodyPart textOfBody = new MimeBodyPart();
            MimeBodyPart attachmentOfBody = new MimeBodyPart();
            Calendar icsCalendar = calendarHandler.cancelRecurEvent();
            CalendarOutputter calendarOutputter = new CalendarOutputter();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            calendarOutputter.output(icsCalendar, byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            DataSource source = new ByteArrayDataSource(bytes, "text/calendar");
            attachmentOfBody.setDataHandler(new DataHandler(source));
            attachmentOfBody.setFileName("calendar");
            attachmentOfBody.setHeader("Content-Class", "urn:content-  classes:calendarmessage");
            attachmentOfBody.setHeader("Content-Disposition", "inline");
            attachmentOfBody.setHeader("Content-ID", "<calendar");
            attachmentOfBody.setHeader("Content-Type", "text/calendar; charset=utf-8; method=CANCEL");
            message = mimeMessageCreator.createAndSetMultipartMessage(mailData.getUsername(), messageStructure, textOfBody, attachmentOfBody);
            mailSender.send(message);
        } catch (MessagingException | IOException e) {
            LOGGER.error(e.getMessage());
            throw new MessageSendingException("Error while sending message. Try again later. ", e);
        }
    }

    @Override
    public void cancelOneRecurEvent(MessageStructure messageStructure) {
        MimeMessage message;
        try {
            MimeBodyPart textOfBody = new MimeBodyPart();
            MimeBodyPart attachmentOfBody = new MimeBodyPart();
            Calendar icsCalendar = calendarHandler.cancelOneRecurEvent();
            CalendarOutputter calendarOutputter = new CalendarOutputter();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            calendarOutputter.output(icsCalendar, byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            DataSource source = new ByteArrayDataSource(bytes, "text/calendar");
            attachmentOfBody.setDataHandler(new DataHandler(source));
            attachmentOfBody.setFileName("calendar");
            attachmentOfBody.setHeader("Content-Class", "urn:content-  classes:calendarmessage");
            attachmentOfBody.setHeader("Content-Disposition", "inline");
            attachmentOfBody.setHeader("Content-ID", "<calendar");
            attachmentOfBody.setHeader("Content-Type", "text/calendar; charset=utf-8; method=REQUEST");
            message = mimeMessageCreator.createAndSetMultipartMessage(mailData.getUsername(), messageStructure, textOfBody, attachmentOfBody);
            mailSender.send(message);
        } catch (MessagingException | IOException e) {
            LOGGER.error(e.getMessage());
            throw new MessageSendingException("Error while sending message. Try again later. ", e);
        }

    }

    @Override
    public void cancelSomeRecurEvents(MessageStructure messageStructure) {
        MimeMessage message;
        try {
            MimeBodyPart textOfBody = new MimeBodyPart();
            MimeBodyPart attachmentOfBody = new MimeBodyPart();
            Calendar icsCalendar = calendarHandler.cancelSomeRecurEvents();
            CalendarOutputter calendarOutputter = new CalendarOutputter();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            calendarOutputter.output(icsCalendar, byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            DataSource source = new ByteArrayDataSource(bytes, "text/calendar");
            attachmentOfBody.setDataHandler(new DataHandler(source));
            attachmentOfBody.setFileName("calendar");
            attachmentOfBody.setHeader("Content-Class", "urn:content-  classes:calendarmessage");
            attachmentOfBody.setHeader("Content-Disposition", "inline");
            attachmentOfBody.setHeader("Content-ID", "<calendar");
            attachmentOfBody.setHeader("Content-Type", "text/calendar; charset=utf-8; method=REQUEST");
            message = mimeMessageCreator.createAndSetMultipartMessage(mailData.getUsername(), messageStructure, textOfBody, attachmentOfBody);
            mailSender.send(message);
        } catch (MessagingException | IOException e) {
            LOGGER.error(e.getMessage());
            throw new MessageSendingException("Error while sending message. Try again later. ", e);
        }
    }

    @Override
    public void createComplexEvent(MessageStructure messageStructure) {
        MimeMessage message;
        try {
            MimeBodyPart textOfBody = new MimeBodyPart();
            MimeBodyPart attachmentOfBody = new MimeBodyPart();
            Calendar icsCalendar = calendarHandler.createComplexEvent();
            CalendarOutputter calendarOutputter = new CalendarOutputter();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            calendarOutputter.output(icsCalendar, byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            DataSource source = new ByteArrayDataSource(bytes, "text/calendar");
            attachmentOfBody.setDataHandler(new DataHandler(source));
            attachmentOfBody.setFileName("calendar");
            attachmentOfBody.setHeader("Content-Class", "urn:content-  classes:calendarmessage");
            attachmentOfBody.setHeader("Content-Disposition", "inline");
            attachmentOfBody.setHeader("Content-ID", "<calendar>");
            attachmentOfBody.setHeader("Content-Type", "text/calendar; charset=utf-8; method=PUBLISH");
            attachmentOfBody.setFileName("inline.ics");
            message = mimeMessageCreator.createAndSetMultipartMessage(mailData.getUsername(), messageStructure, textOfBody, attachmentOfBody);
            mailSender.send(message);
        } catch (MessagingException | IOException e) {
            LOGGER.error(e.getMessage());
            throw new MessageSendingException("Error while sending message. Try again later. ", e);
        }
    }

    @Override
    public void cancelComplexEvent(MessageStructure messageStructure) {
        MimeMessage message;
        try {
            MimeBodyPart textOfBody = new MimeBodyPart();
            MimeBodyPart attachmentOfBody = new MimeBodyPart();
            Calendar icsCalendar = calendarHandler.cancelComplexEvent();
            CalendarOutputter calendarOutputter = new CalendarOutputter();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            calendarOutputter.output(icsCalendar, byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            DataSource source = new ByteArrayDataSource(bytes, "text/calendar");
            attachmentOfBody.setDataHandler(new DataHandler(source));
            attachmentOfBody.setFileName("calendar");
            attachmentOfBody.setHeader("Content-Class", "urn:content-  classes:calendarmessage");
            attachmentOfBody.setHeader("Content-Disposition", "inline");
            attachmentOfBody.setHeader("Content-ID", "<calendar>");
            attachmentOfBody.setHeader("Content-Type", "text/calendar; charset=utf-8; method=PUBLISH");
            attachmentOfBody.setFileName("inline.ics");
            message = mimeMessageCreator.createAndSetMultipartMessage(mailData.getUsername(), messageStructure, textOfBody, attachmentOfBody);
            mailSender.send(message);
        } catch (MessagingException | IOException e) {
            LOGGER.error(e.getMessage());
            throw new MessageSendingException("Error while sending message. Try again later. ", e);
        }
    }

    @Override
    public void rescheduleComplexEvents(MessageStructure messageStructure){
        MimeMessage message;
        try {
            MimeBodyPart textOfBody = new MimeBodyPart();
            MimeBodyPart attachmentOfBody = new MimeBodyPart();
            Calendar icsCalendar = calendarHandler.rescheduleComplexEvents(messageStructure);
            CalendarOutputter calendarOutputter = new CalendarOutputter();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            calendarOutputter.output(icsCalendar, byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            DataSource source = new ByteArrayDataSource(bytes, "text/calendar");
            attachmentOfBody.setDataHandler(new DataHandler(source));
            attachmentOfBody.setFileName("calendar");
            attachmentOfBody.setHeader("Content-Class", "urn:content-  classes:calendarmessage");
            attachmentOfBody.setHeader("Content-Disposition", "inline");
            attachmentOfBody.setHeader("Content-ID", "<calendar>");
            attachmentOfBody.setHeader("Content-Type", "text/calendar; charset=utf-8; method=PUBLISH");
            attachmentOfBody.setFileName("inline.ics");
            message = mimeMessageCreator.createAndSetMultipartMessage(mailData.getUsername(), messageStructure, textOfBody, attachmentOfBody);
            mailSender.send(message);
        } catch (MessagingException | IOException e) {
            LOGGER.error(e.getMessage());
            throw new MessageSendingException("Error while sending message. Try again later. ", e);
        }

        }
}
