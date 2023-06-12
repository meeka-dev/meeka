package app.meeka.domain.exception;

import app.meeka.core.exception.BusinessException;

import static app.meeka.domain.exception.DomainErrorCodes.PASSWORD_ERR_EXCEPTION;

public class PasswordErrException extends BusinessException {

    public PasswordErrException() {
        super(PASSWORD_ERR_EXCEPTION);
    }
}
