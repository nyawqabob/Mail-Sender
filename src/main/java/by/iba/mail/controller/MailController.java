package by.iba.mail.controller;

import by.iba.mail.entity.MessageStructure;
import by.iba.mail.service.v2.MailServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/")
public class MailController {

    @Autowired
    private MailServiceImpl mailService;


    @ApiOperation(value = "Send message without any files", response = MessageStructure.class)
    @RequestMapping(value = "/send/simple", method = RequestMethod.POST)
    @ResponseBody
    public void send(@RequestBody MessageStructure messageStructure) {
        mailService.sendMessage(messageStructure);
    }

    @ApiOperation(value = "Send message with attachment", response = MessageStructure.class)
    @RequestMapping(value="/send/attachment", method = RequestMethod.POST)
    @ResponseBody
    public void sendWithAttachment(@RequestBody MessageStructure messageStructure) {
        mailService.sendMessageWithAttachment(messageStructure);
    }

    @ApiOperation(value = "Send message with inline", response = MessageStructure.class)
    @RequestMapping(value="/send/inline", method = RequestMethod.POST)
    @ResponseBody
    public void sendWithInline(@RequestBody MessageStructure messageStructure) {
        mailService.sendMessageWithInlineAttachment(messageStructure);
    }

    @ApiOperation(value = "Redirect to swagger ui page")
    @RequestMapping(method = RequestMethod.GET)
    public String redirectToSwaggerUi() {
        return "redirect:/swagger-ui.html";
    }

}
