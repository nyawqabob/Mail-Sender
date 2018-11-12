package by.iba.mail.controller.advice;

import by.iba.mail.config.exception.MessageSendingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MessageSendingException.class)
    protected ResponseEntity<AdditionalException> handleSendingExceptions(MessageSendingException ex) {
        return new ResponseEntity<>(new AdditionalException(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
