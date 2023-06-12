package app.meeka.domain.exception;

import app.meeka.core.exception.BusinessException;

public class InvalidUsernameFormatException extends BusinessException {

    public InvalidUsernameFormatException() {
        super(DomainErrorCodes.INVALID_USERNAME_FORMAT_EXCEPTION);
    }
}
