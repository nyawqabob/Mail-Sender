package by.iba.mail.config.calendar;

import by.iba.mail.entity.MessageStructure;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.DateList;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Recur;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.parameter.PartStat;
import net.fortuna.ical4j.model.parameter.Role;
import net.fortuna.ical4j.model.parameter.Rsvp;
import net.fortuna.ical4j.model.property.*;
import net.fortuna.ical4j.util.FixedUidGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CalendarHandler {

    public static final Logger LOGGER = LogManager.getLogger(CalendarHandler.class);
    public static Uid uidForSimple;
    public static Uid uidForRecur;
    public static Uid uidForFirstCompex;
    public static Uid uidForSecondComplex;

    public Calendar createRecurEvent() {
        String eventName = "Recur event";
        DateTime start = null;
        Recur recur = null;
        DateTime end = null;
        DateList exDates = new DateList();
        try {
            start = new DateTime("20181115T153000Z");
            exDates.add(start);
            end = new DateTime("20181115T163000Z");
            recur = new Recur("FREQ=WEEKLY;BYDAY=FR;UNTIL=20181215T153000Z");
        } catch (ParseException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        VEvent meeting = new VEvent(start, end, eventName);
        Calendar icsCalendar = new Calendar();
        icsCalendar.getProperties().add(new ProdId("-//iba"));
        icsCalendar.getProperties().add(CalScale.GREGORIAN);
        icsCalendar.getProperties().add(Version.VERSION_2_0);
        icsCalendar.getProperties().add(Method.REQUEST);
        try {
            meeting.getProperties().add(new XProperty("X-LOTUS-UPDATE-SEQ", "1"));
            meeting.getProperties().add(new RRule(recur));
            meeting.getProperties().getProperty("RRULE");
            meeting.getProperties().addAll(getXProperties());
            meeting.getProperties().add(new ExDate(exDates));
            meeting.getProperties().add(new Description("Description"));
            Attendee dev1 = new Attendee(URI.create("mailto:shahrai.robert@gmail.com"));
            dev1.getParameters().add(Role.REQ_PARTICIPANT);
            dev1.getParameters().add(Rsvp.FALSE);
            meeting.getProperties().add(dev1);
            meeting.getProperties().add(new Sequence("0"));
            meeting.getProperties().add(new Organizer("MAILTO:robertshahrai@gmail.com"));
            FixedUidGenerator fixedUidGenerator = new FixedUidGenerator("uid_generator");
            uidForRecur = fixedUidGenerator.generateUid();
            LOGGER.info(uidForRecur.toString());
            meeting.getProperties().add(uidForRecur);
        } catch (SocketException | URISyntaxException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        icsCalendar.getComponents().add(meeting);
        icsCalendar.validate();
        LOGGER.info("Calendar was validated");
        return icsCalendar;
    }

    public Calendar createSimpleEvent() {
        String eventName = "Simple event";
        DateTime start = null;
        DateTime end = null;
        try {
            start = new DateTime("20181115T1530000Z");
            end = new DateTime("20181115T1600000Z");
        } catch (ParseException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        VEvent meeting = new VEvent(start, end, eventName);
        Calendar icsCalendar = new Calendar();
        Attendee attendee = new Attendee(URI.create("mailto:shahrai.robert@gmail.com"));
        attendee.getParameters().add(Role.REQ_PARTICIPANT);
        attendee.getParameters().add(PartStat.ACCEPTED);
        attendee.getParameters().add(Rsvp.TRUE);
        meeting.getProperties().add(attendee);
        icsCalendar.getProperties().add(new ProdId("-//iba"));
        icsCalendar.getProperties().add(CalScale.GREGORIAN);
        icsCalendar.getProperties().add(Version.VERSION_2_0);
        icsCalendar.getProperties().add(Method.REQUEST);
        try {
            meeting.getProperties().add(new Organizer("MAILTO:robertshahrai@gmail.com"));
            meeting.getProperties().add(new Description("Description"));
            FixedUidGenerator fixedUidGenerator = new FixedUidGenerator("uid_generator");
            uidForSimple = fixedUidGenerator.generateUid();
            LOGGER.info(uidForSimple.toString());
            meeting.getProperties().add(uidForSimple);
            meeting.getProperties().add(new Sequence("0"));
        } catch (URISyntaxException | SocketException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        icsCalendar.getComponents().add(meeting);
        icsCalendar.validate();
        LOGGER.info("Calendar was validated");
        return icsCalendar;
    }

    public Calendar createComplexEvent() {
        DateTime startFirst = null;
        DateTime endFirst = null;
        DateTime startSecond = null;
        DateTime endSecond = null;
        try {
            startFirst = new DateTime("20181120T1530000Z");
            endFirst = new DateTime("20181120T1600000Z");
            startSecond = new DateTime("20181121T154000Z");
            endSecond = new DateTime("20181121T164000Z");

        } catch (ParseException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        VEvent firstMeeting = new VEvent(startFirst, endFirst, "First meeting from complex event");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        VEvent secondMeeting = new VEvent(startSecond, endSecond, "Second meeting from complex event");
        Calendar icsCalendar = new Calendar();
        Attendee attendee = new Attendee(URI.create("mailto:shahrai.robert@gmail.com"));
        attendee.getParameters().add(Role.REQ_PARTICIPANT);
        attendee.getParameters().add(PartStat.ACCEPTED);
        attendee.getParameters().add(Rsvp.TRUE);
        icsCalendar.getProperties().add(new ProdId("-//iba"));
        icsCalendar.getProperties().add(CalScale.GREGORIAN);
        icsCalendar.getProperties().add(Version.VERSION_2_0);
        icsCalendar.getProperties().add(Method.PUBLISH);
        try {
            //firstMeeting.getProperties().add(attendee);
            // firstMeeting.getProperties().addAll(getXProperties());
            // secondMeeting.getProperties().addAll(getXProperties());
            //secondMeeting.getProperties().add(attendee);
            firstMeeting.getProperties().add(new Organizer("MAILTO:robertshahrai@gmail.com"));
            firstMeeting.getProperties().add(new Description("Description of first meeting from complex event"));
            secondMeeting.getProperties().add(new Organizer("MAILTO:robertshahrai@gmail.com"));
            secondMeeting.getProperties().add(new Description("Description of second meeting from complex event"));
            FixedUidGenerator fixedUidGenerator = new FixedUidGenerator("uid_generator");
            uidForFirstCompex = fixedUidGenerator.generateUid();
            uidForSecondComplex = fixedUidGenerator.generateUid();
            firstMeeting.getProperties().add(uidForFirstCompex);
            secondMeeting.getProperties().add(uidForSecondComplex);
            firstMeeting.getProperties().add(new Sequence("0"));
            secondMeeting.getProperties().add(new Sequence("0"));
        } catch (URISyntaxException | SocketException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        icsCalendar.getComponents().add(firstMeeting);
        icsCalendar.getComponents().add(secondMeeting);
        icsCalendar.validate();
        LOGGER.info("Calendar was validated");
        return icsCalendar;
    }


    public Calendar cancelEvent() {
        DateTime start = null;
        try {
            start = new DateTime("20181115T1530000Z");
        } catch (ParseException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        VEvent meeting = new VEvent(start, "Event was cancelled");
        Calendar icsCalendar = new Calendar();
        icsCalendar.getProperties().add(new ProdId("-//iba"));
        icsCalendar.getProperties().add(CalScale.GREGORIAN);
        icsCalendar.getProperties().add(Version.VERSION_2_0);
        icsCalendar.getProperties().add(Method.CANCEL);
        try {
            meeting.getProperties().add(new Organizer("MAILTO:robertshahrai@gmail.com"));
            Attendee dev1 = new Attendee(URI.create("mailto:shahrai.robert@gmail.com"));
            dev1.getParameters().add(Role.REQ_PARTICIPANT);
            meeting.getProperties().add(dev1);
            meeting.getProperties().add(Status.VEVENT_CANCELLED);
            meeting.getProperties().add(uidForSimple);
            meeting.getProperties().add(new Sequence("1"));
        } catch (URISyntaxException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        icsCalendar.getComponents().add(meeting);
        icsCalendar.validate();
        LOGGER.info("Calendar was validated");
        return icsCalendar;
    }

    public Calendar rescheduleEvent() {
        DateTime start = null;
        DateTime end = null;
        try {
            start = new DateTime("20181116T1530000Z");
            end = new DateTime("20181116T1600000Z");
        } catch (ParseException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        VEvent meeting = new VEvent(start, end, "Event $name$ was rescheduled");
        Calendar icsCalendar = new Calendar();
        icsCalendar.getProperties().add(new ProdId("-//ibaa"));
        icsCalendar.getProperties().add(CalScale.GREGORIAN);
        icsCalendar.getProperties().add(Version.VERSION_2_0);
        icsCalendar.getProperties().add(Method.REQUEST);
        try {
            meeting.getProperties().add(new Organizer("MAILTO:robertshahrai@gmail.com"));
            Attendee dev1 = new Attendee(URI.create("mailto:shahrai.robert@gmail.com"));
            dev1.getParameters().add(Role.REQ_PARTICIPANT);
            dev1.getParameters().add(Rsvp.FALSE);
            meeting.getProperties().add(dev1);
            meeting.getProperties().add(uidForSimple);
            meeting.getProperties().add(new Sequence("2"));
        } catch (URISyntaxException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        icsCalendar.getComponents().add(meeting);
        icsCalendar.validate();
        LOGGER.info("Calendar was validated");
        return icsCalendar;
    }

    public Calendar updateSimpleEvent() {
        DateTime start = null;
        DateTime end = null;
        try {
            start = new DateTime("20181115T1530000Z");
            end = new DateTime("20181115T1600000Z");
        } catch (ParseException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        VEvent meeting = new VEvent(start, end, "Simple event");
        Calendar icsCalendar = new Calendar();
        icsCalendar.getProperties().add(new ProdId("-//ibaa"));
        icsCalendar.getProperties().add(CalScale.GREGORIAN);
        icsCalendar.getProperties().add(Version.VERSION_2_0);
        icsCalendar.getProperties().add(Method.REQUEST);
        try {
            meeting.getProperties().add(new Organizer("MAILTO:robertshahrai@gmail.com"));
            Attendee attendee = new Attendee(URI.create("mailto:shahrai.robert@gmail.com"));
            attendee.getParameters().add(Role.REQ_PARTICIPANT);
            attendee.getParameters().add(Rsvp.FALSE);
            attendee.getParameters().add(PartStat.ACCEPTED);
            meeting.getProperties().add(attendee);
            meeting.getProperties().add(uidForSimple);
            XProperty lotusUpdateSeq = new XProperty("X-LOTUS-UPDATE-SEQ", "0");
            meeting.getProperties().add(lotusUpdateSeq);
            meeting.getProperties().add(new Description("Desciption after update"));
            meeting.getProperties().add(new Sequence("0"));
        } catch (URISyntaxException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        icsCalendar.getComponents().add(meeting);
        icsCalendar.validate();
        LOGGER.info("Calendar was validated");
        return icsCalendar;
    }

    public Calendar cancelRecurEvent() {
        String eventName = "Cancel all recur events";
        DateTime start = null;
        try {
            start = new DateTime("20181116T153000Z");
        } catch (ParseException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        VEvent meeting = new VEvent(start, eventName);
        Calendar icsCalendar = new Calendar();
        icsCalendar.getProperties().add(new ProdId("-//iba"));
        icsCalendar.getProperties().add(CalScale.GREGORIAN);
        icsCalendar.getProperties().add(Version.VERSION_2_0);
        icsCalendar.getProperties().add(Method.CANCEL);
        //icsCalendar.getProperties().add(new RecurrenceId(start));
        try {
            meeting.getProperties().addAll(getXProperties());
            meeting.getProperties().add(new Description("Description after update"));
            Attendee dev1 = new Attendee(URI.create("mailto:shahrai.robert@gmail.com"));
            dev1.getParameters().add(Role.REQ_PARTICIPANT);
            dev1.getParameters().add(Rsvp.FALSE);
            meeting.getProperties().add(dev1);
            meeting.getProperties().add(new Organizer("MAILTO:robertshahrai@gmail.com"));
            LOGGER.info(uidForRecur.toString());
            meeting.getProperties().add(new Sequence("0"));
            meeting.getProperties().add(uidForRecur);
        } catch (URISyntaxException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        icsCalendar.getComponents().add(meeting);
        icsCalendar.validate();
        LOGGER.info("Calendar was validated");
        return icsCalendar;
    }

    public Calendar cancelOneRecurEvent() {
        String eventName = "Cancel one recur event";
        DateTime start = null;
        try {
            start = new DateTime("20181123T153000Z");
        } catch (ParseException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        VEvent meeting = new VEvent(start, eventName);
        Calendar icsCalendar = new Calendar();
        icsCalendar.getProperties().add(new ProdId("-//iba"));
        icsCalendar.getProperties().add(CalScale.GREGORIAN);
        icsCalendar.getProperties().add(Version.VERSION_2_0);
        icsCalendar.getProperties().add(Method.REQUEST);
        try {
            meeting.getProperties().add(new RecurrenceId("20181123T153000Z"));
            //meeting.getProperties().add(new RecurrenceId("20181130T153000Z"));
            meeting.getProperties().addAll(getXProperties());
            meeting.getProperties().add(new Description("Description after update"));
            Attendee dev1 = new Attendee(URI.create("mailto:shahrai.robert@gmail.com"));
            dev1.getParameters().add(Role.REQ_PARTICIPANT);
            dev1.getParameters().add(Rsvp.FALSE);
            meeting.getProperties().add(dev1);
            meeting.getProperties().add(new Organizer("MAILTO:robertshahrai@gmail.com"));
            //LOGGER.info(uidForRecur.toString());
            meeting.getProperties().add(new Sequence("1"));
            meeting.getProperties().add(uidForRecur);
        } catch (ParseException | URISyntaxException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        icsCalendar.getComponents().add(meeting);
        icsCalendar.validate();
        LOGGER.info("Calendar was validated");
        return icsCalendar;
    }

    public Calendar cancelSomeRecurEvents() {
        String eventName = "Cancel some recur events";
        DateTime start = null;
        DateList exDates = new DateList();
        Recur recur = null;
        try {
            recur = new Recur("FREQ=WEEKLY;BYDAY=FR;UNTIL=20181215T153000Z");
            start = new DateTime("20181115T153000Z");
            exDates.add(new DateTime("20181123T153000Z"));
            exDates.add(new DateTime("20181130T153000Z"));
            exDates.add(start);
        } catch (ParseException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        VEvent meeting = new VEvent(start, eventName);
        Calendar icsCalendar = new Calendar();
        icsCalendar.getProperties().add(new ProdId("-//iba"));
        icsCalendar.getProperties().add(CalScale.GREGORIAN);
        icsCalendar.getProperties().add(Version.VERSION_2_0);
        icsCalendar.getProperties().add(Method.REQUEST);
        try {
            //meeting.getProperties().add(new RecurrenceId("20181130T153000Z"));
            meeting.getProperties().add(new XProperty("X-LOTUS-UPDATE-SEQ", "2"));
            meeting.getProperties().add(new RRule(recur));
            meeting.getProperties().addAll(getXProperties());
            meeting.getProperties().add(new Description("Description after update"));
            Attendee dev1 = new Attendee(URI.create("mailto:shahrai.robert@gmail.com"));
            dev1.getParameters().add(Role.REQ_PARTICIPANT);
            dev1.getParameters().add(Rsvp.FALSE);
            meeting.getProperties().add(dev1);
            meeting.getProperties().add(new ExDate(exDates));
            meeting.getProperties().add(new Organizer("MAILTO:robertshahrai@gmail.com"));
            //LOGGER.info(uidForRecur.toString());
            meeting.getProperties().add(new Sequence("1"));
            meeting.getProperties().add(uidForRecur);
        } catch (URISyntaxException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        icsCalendar.getComponents().add(meeting);
        icsCalendar.validate();
        LOGGER.info("Calendar was validated");
        return icsCalendar;
    }

    public Calendar cancelComplexEvent() {
        DateTime startFirst = null;
        DateTime startSecond = null;
        try {
            startFirst = new DateTime("20181120T1530000Z");
            startSecond = new DateTime("20181121T154000Z");
        } catch (ParseException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        VEvent firstMeeting = new VEvent(startFirst, "Cancel First meeting from complex event");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        VEvent secondMeeting = new VEvent(startSecond, "Cancel Second meeting from complex event");
        Calendar icsCalendar = new Calendar();
        Attendee attendee = new Attendee(URI.create("mailto:shahrai.robert@gmail.com"));
        attendee.getParameters().add(Role.REQ_PARTICIPANT);
        attendee.getParameters().add(PartStat.ACCEPTED);
        attendee.getParameters().add(Rsvp.TRUE);
        icsCalendar.getProperties().add(new ProdId("-//iba"));
        icsCalendar.getProperties().add(CalScale.GREGORIAN);
        icsCalendar.getProperties().add(Version.VERSION_2_0);
        icsCalendar.getProperties().add(Method.PUBLISH);
        try {
            //firstMeeting.getProperties().add(attendee);
            //firstMeeting.getProperties().addAll(getXProperties());
            //secondMeeting.getProperties().addAll(getXProperties());
            //secondMeeting.getProperties().add(attendee);
            firstMeeting.getProperties().add(new Organizer("MAILTO:robertshahrai@gmail.com"));
            firstMeeting.getProperties().add(new Description("Description of first meeting from complex event"));
            secondMeeting.getProperties().add(new Organizer("MAILTO:robertshahrai@gmail.com"));
            secondMeeting.getProperties().add(new Description("Description of second meeting from complex event"));
            firstMeeting.getProperties().add(uidForFirstCompex);
            secondMeeting.getProperties().add(uidForSecondComplex);
            firstMeeting.getProperties().add(new Sequence("0"));
            secondMeeting.getProperties().add(new Sequence("0"));
        } catch (URISyntaxException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        icsCalendar.getComponents().add(firstMeeting);
        icsCalendar.getComponents().add(secondMeeting);
        icsCalendar.validate();
        LOGGER.info("Calendar was validated");
        return icsCalendar;
    }

    public Calendar rescheduleComplexEvents(MessageStructure messageStructure){
        DateTime startFirst = null;
        DateTime startSecond = null;
        try {
            startFirst = new DateTime("20181122T1530000Z");
            startSecond = new DateTime("20181123T154000Z");
        } catch (ParseException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        VEvent firstMeeting = new VEvent(startFirst, "Reschedule First meeting from complex event");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        VEvent secondMeeting = new VEvent(startSecond, "Reschedule Second meeting from complex event");
        Calendar icsCalendar = new Calendar();
        Attendee attendee = new Attendee(URI.create("mailto:shahrai.robert@gmail.com"));
        attendee.getParameters().add(Role.REQ_PARTICIPANT);
        attendee.getParameters().add(PartStat.ACCEPTED);
        attendee.getParameters().add(Rsvp.TRUE);
        icsCalendar.getProperties().add(new ProdId("-//iba"));
        icsCalendar.getProperties().add(CalScale.GREGORIAN);
        icsCalendar.getProperties().add(Version.VERSION_2_0);
        icsCalendar.getProperties().add(Method.PUBLISH);
        try {
            //firstMeeting.getProperties().add(attendee);
            //firstMeeting.getProperties().addAll(getXProperties());
            //secondMeeting.getProperties().addAll(getXProperties());
            //secondMeeting.getProperties().add(attendee);
            firstMeeting.getProperties().add(new Organizer("MAILTO:robertshahrai@gmail.com"));
            firstMeeting.getProperties().add(new Description("Description of first meeting from complex event"));
            secondMeeting.getProperties().add(new Organizer("MAILTO:robertshahrai@gmail.com"));
            secondMeeting.getProperties().add(new Description("Description of second meeting from complex event"));
            firstMeeting.getProperties().add(uidForFirstCompex);
            secondMeeting.getProperties().add(uidForSecondComplex);
            firstMeeting.getProperties().add(new Sequence("2"));
            secondMeeting.getProperties().add(new Sequence("2"));
        } catch (URISyntaxException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        icsCalendar.getComponents().add(firstMeeting);
        icsCalendar.getComponents().add(secondMeeting);
        icsCalendar.validate();
        LOGGER.info("Calendar was validated");
        return icsCalendar;

    }


    private List<XProperty> getXProperties() {
        List<XProperty> xProperties = new ArrayList<>();
        xProperties.add(new XProperty("X-LOTUS-BROADCAST", "TRUE"));
        xProperties.add(new XProperty("X-LOTUS-PREVENTCOUNTER", "FALSE"));
        xProperties.add(new XProperty("X-MICROSOFT-DISALLOW-COUNTER", "TRUE"));
        xProperties.add(new XProperty("X-MS-OLK-FORCEINSPECTOROPEN", "TRUE"));
        xProperties.add(new XProperty("X-MS-ATTACHMENT", "YES"));
        xProperties.add(new XProperty("X-LOTUS-CHARSET", "UTF-8"));
        xProperties.add(new XProperty("X-LOTUS-PREVENTDELEGATION", "TRUE"));
        xProperties.add(new XProperty("X-LOTUS-NOTESVERSION", "2"));
        xProperties.add(new XProperty("X-LOTUS-APPTTYPE", "3"));
        xProperties.add(new XProperty("X-LOTUS-UPDATE-SUBJECT", "New Subject For Lotus"));
        return xProperties;
    }
}