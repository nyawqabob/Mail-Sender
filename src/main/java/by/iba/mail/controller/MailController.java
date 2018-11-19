package by.iba.mail.controller;

import by.iba.mail.entity.MessageStructure;
import by.iba.mail.entity.result.ResponseResult;
import by.iba.mail.entity.result.Result;
import by.iba.mail.service.v1.MailServiceImpl;
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

    @ApiOperation(value = "Send message without any files", response = ResponseResult.class)
    @RequestMapping(value = "/send/simple", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult send(@RequestBody MessageStructure messageStructure) {
        mailService.sendMessage(messageStructure);
        return new ResponseResult(Result.SUCCESSFULLY);
    }

    @ApiOperation(value = "Send message with attachment", response = ResponseResult.class)
    @RequestMapping(value = "/send/attachment", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult sendWithAttachment(@RequestBody MessageStructure messageStructure) {
        mailService.sendMessageWithAttachment(messageStructure);
        return new ResponseResult(Result.SUCCESSFULLY);
    }

    @ApiOperation(value = "Send message with inline", response = ResponseResult.class)
    @RequestMapping(value = "/send/inline", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult sendWithInline(@RequestBody MessageStructure messageStructure) {
        mailService.sendMessageWithInline(messageStructure);
        return new ResponseResult(Result.SUCCESSFULLY);
    }

    @ApiOperation(value = "Redirect to swagger ui page")
    @RequestMapping(method = RequestMethod.GET)
    public String redirectToSwaggerUi() {
        return "redirect:/swagger-ui.html";
    }


    @RequestMapping(value = "/calendar/add/simple", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult calendarAdd(@RequestBody MessageStructure messageStructure) {
        mailService.requestEvent(messageStructure);
        return new ResponseResult(Result.SUCCESSFULLY);
    }

    @RequestMapping(value = "/calendar/cancel/simple", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult calendarDelete(@RequestBody MessageStructure messageStructure) {
        mailService.cancelEvent(messageStructure);
        return new ResponseResult(Result.SUCCESSFULLY);
    }

    @RequestMapping(value = "/calendar/reschedule/simple", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult calendarReschedule(@RequestBody MessageStructure messageStructure) {
        mailService.rescheduleEvent(messageStructure);
        return new ResponseResult(Result.SUCCESSFULLY);
    }

    @RequestMapping(value = "/calendar/update/simple", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult calendarUpdate(@RequestBody MessageStructure messageStructure) {
        mailService.updateEvent(messageStructure);
        return new ResponseResult(Result.SUCCESSFULLY);
    }

    @RequestMapping(value = "/calendar/add/recur", method=RequestMethod.POST)
    @ResponseBody
    public ResponseResult calendarAddRecur(@RequestBody MessageStructure messageStructure){
        mailService.addRecurEvent(messageStructure);
        return new ResponseResult(Result.SUCCESSFULLY);
    }

    @RequestMapping(value = "/calendar/cancel/recur", method=RequestMethod.POST)
    @ResponseBody
    public ResponseResult calendarCancelRecur(@RequestBody MessageStructure messageStructure){
        mailService.cancelRecurEvent(messageStructure);
        return new ResponseResult(Result.SUCCESSFULLY);
    }

    @RequestMapping(value = "/calendar/cancel/one_recur", method=RequestMethod.POST)
    @ResponseBody
    public ResponseResult calendarCancelOneRecur(@RequestBody MessageStructure messageStructure){
        mailService.cancelOneRecurEvent(messageStructure);
        return new ResponseResult(Result.SUCCESSFULLY);
    }

    @RequestMapping(value = "/calendar/cancel/some_recur", method=RequestMethod.POST)
    @ResponseBody
    public ResponseResult calendarCancelSomeRecur(@RequestBody MessageStructure messageStructure){
        mailService.cancelSomeRecurEvents(messageStructure);
        return new ResponseResult(Result.SUCCESSFULLY);
    }

    @RequestMapping(value = "/calendar/cancel/complex", method=RequestMethod.POST)
    @ResponseBody
    public ResponseResult calendarCancelomplex(@RequestBody MessageStructure messageStructure){
        mailService.cancelComplexEvent(messageStructure);
        return new ResponseResult(Result.SUCCESSFULLY);
    }

    @RequestMapping(value = "/calendar/add/complex", method=RequestMethod.POST)
    @ResponseBody
    public ResponseResult calendarAddComplex(@RequestBody MessageStructure messageStructure){
        mailService.createComplexEvent(messageStructure);
        return new ResponseResult(Result.SUCCESSFULLY);
    }

    @RequestMapping(value = "/calendar/reschedule/complex", method=RequestMethod.POST)
    @ResponseBody
    public ResponseResult calendarRescheduleComplex(@RequestBody MessageStructure messageStructure){
        mailService.rescheduleComplexEvents(messageStructure);
        return new ResponseResult(Result.SUCCESSFULLY);
    }


}
