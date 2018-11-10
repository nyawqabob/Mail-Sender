package by.iba.mail.service;

import by.iba.mail.entity.MessageStructure;

public interface MailService {

    void sendMessage(MessageStructure messageStructure);

    void sendMessageWithAttachment(MessageStructure messageStructure);

    void sendMessageWithInlineAttachment(MessageStructure messageStructure);

}
