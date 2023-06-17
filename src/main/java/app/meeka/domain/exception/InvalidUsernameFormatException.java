package app.meeka.domain.exception;

import app.meeka.core.exception.BusinessException;

import static app.meeka.domain.exception.DomainErrorCodes.INVALID_USERNAME_FORMAT_EXCEPTION;

public class InvalidUsernameFormatException extends BusinessException {

    public InvalidUsernameFormatException() {
        super(INVALID_USERNAME_FORMAT_EXCEPTION);
    }
}
