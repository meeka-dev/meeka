package app.meeka.domain.exception;

import app.meeka.core.exception.BusinessException;

import static app.meeka.domain.exception.DomainErrorCodes.INVALID_PASSWORD_FORMAT_EXCEPTION;

public class InvalidPasswordFormatException extends BusinessException {

    public InvalidPasswordFormatException() {
        super(INVALID_PASSWORD_FORMAT_EXCEPTION);
    }
}
