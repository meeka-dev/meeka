package app.meeka.core.rest.exception;

import app.meeka.core.exception.BusinessException;
import app.meeka.core.exception.SystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.internalServerError;

@ControllerAdvice
public class ExceptionControllerAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

    public static final String DEFAULT_BUSINESS_EXCEPTION_MESSAGE_TEMPLATE = "No error message found under the error code <%s> for the locale <%s>.";
    public static final String SYSTEM_EXCEPTION_MESSAGE = "A system error occurred.";

    private final MessageSource messageSource;

    public ExceptionControllerAdvice(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler({ BusinessException.class })
    public ResponseEntity<ErrorResponse> handleBusinessExceptions(BusinessException exception, WebRequest request) {
        var errorMessage = messageSource.getMessage(
                exception.errorCode,
                exception.args.toArray(),
                DEFAULT_BUSINESS_EXCEPTION_MESSAGE_TEMPLATE.formatted(exception.errorCode, request.getLocale()),
                request.getLocale()
        );
        return badRequest().body(new ErrorResponse(
                exception.errorCode,
                errorMessage
        ));
    }

    @ExceptionHandler({ SystemException.class })
    public ResponseEntity<ErrorResponse> handleSystemExceptions(SystemException exception) {
        LOGGER.error(
                "A system exception occurred. Error code: <%s>, description: <%s>".formatted(exception.errorCode, exception.description),
                exception
        );
        return internalServerError().body(new ErrorResponse(
                exception.errorCode,
                SYSTEM_EXCEPTION_MESSAGE
        ));
    }
}
