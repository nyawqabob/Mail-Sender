package by.iba.mail.controller;

import by.iba.mail.entity.MessageStructure;
import by.iba.mail.service.v2.MailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MailController {

    private MailServiceImpl mailService;

    @Autowired
    public MailController(MailServiceImpl mailService) {
        this.mailService = mailService;
    }

    @RequestMapping(name = "/send", method = RequestMethod.POST)
    @ResponseBody
    public void send(@RequestBody MessageStructure messageStructure) {
        mailService.sendMessage(messageStructure);
    }

    @RequestMapping("/send/attachment")
    @ResponseBody
    public void sendWithAttachment(@RequestBody MessageStructure messageStructure) {
        mailService.sendMessageWithAttachment(messageStructure);
    }

    @RequestMapping("/send/inline")
    @ResponseBody
    public void sendWithInline(@RequestBody MessageStructure messageStructure) {
        mailService.sendMessageWithInlineAttachment(messageStructure);
    }


}
