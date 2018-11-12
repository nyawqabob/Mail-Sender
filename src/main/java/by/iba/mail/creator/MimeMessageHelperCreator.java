package by.iba.mail.creator;

import by.iba.mail.entity.MessageStructure;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class MimeMessageHelperCreator {

    public MimeMessageHelper createAndSet(MimeMessage mimeMessage, MessageStructure messageStructure, String from) throws MessagingException {
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(messageStructure.getTo());
        mimeMessageHelper.setFrom(from);
        mimeMessageHelper.setSubject(messageStructure.getSubject());
        if (messageStructure.isHtml()) {
            mimeMessageHelper.setText("<html><body>" + messageStructure.getContent() + "</html></body>", true);
        } else {
            mimeMessageHelper.setText(messageStructure.getContent());
        }
        return mimeMessageHelper;
    }
}
