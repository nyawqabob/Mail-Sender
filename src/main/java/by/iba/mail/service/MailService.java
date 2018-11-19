package by.iba.mail.service;

import by.iba.mail.entity.MessageStructure;

public interface MailService {

    void sendMessage(MessageStructure messageStructure);

    void sendMessageWithAttachment(MessageStructure messageStructure);

    void sendMessageWithInline(MessageStructure messageStructure);

    void requestEvent(MessageStructure messageStructure);

    void cancelEvent(MessageStructure messageStructure);

    void rescheduleEvent(MessageStructure messageStructure);

    void updateEvent(MessageStructure messageStructure);

    void addRecurEvent(MessageStructure messageStructure);

    void cancelRecurEvent(MessageStructure messageStructure);

    void cancelOneRecurEvent(MessageStructure messageStructure);

    void cancelSomeRecurEvents(MessageStructure messageStructure);

    void createComplexEvent(MessageStructure messageStructure);

    void cancelComplexEvent(MessageStructure messageStructure);

    void rescheduleComplexEvents(MessageStructure messageStructure);
}
