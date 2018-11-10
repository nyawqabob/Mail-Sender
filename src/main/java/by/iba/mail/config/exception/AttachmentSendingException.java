package by.iba.mail.config.exception;

public class AttachmentSendingException extends RuntimeException {
    public AttachmentSendingException(String message) {
        super(message);
    }

    public AttachmentSendingException(String message, Throwable cause) {
        super(message, cause);
    }

    public AttachmentSendingException(Throwable cause) {
        super(cause);
    }
}
