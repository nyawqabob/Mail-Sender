package by.iba.mail.exception.advice;

import by.iba.mail.exception.MessageSendingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(MessageSendingException.class)
    protected ResponseEntity<AdditionalException> handleSendingExceptions(MessageSendingException ex) {
        return new ResponseEntity<>(new AdditionalException(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
