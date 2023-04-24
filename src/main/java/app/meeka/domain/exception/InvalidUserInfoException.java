package app.meeka.domain.exception;

import app.meeka.core.exception.BusinessException;

import static app.meeka.domain.exception.DomainErrorCodes.INVALID_USER_INFO_EXCEPTION;

public class InvalidUserInfoException extends BusinessException {
    public InvalidUserInfoException() {
        super(INVALID_USER_INFO_EXCEPTION);
    }
}
