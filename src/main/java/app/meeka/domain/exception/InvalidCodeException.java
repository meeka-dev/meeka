package app.meeka.domain.exception;

import app.meeka.core.exception.BusinessException;

import static app.meeka.domain.exception.DomainErrorCodes.INVALID_LOGIN_CODE_EXCEPTION;

public class InvalidCodeException extends BusinessException {
    public InvalidCodeException() {
        super(INVALID_LOGIN_CODE_EXCEPTION);
    }
}
