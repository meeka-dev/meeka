package app.meeka.domain.exception;

import app.meeka.core.exception.BusinessException;

import static app.meeka.domain.exception.DomainErrorCodes.PASSWORD_ERROR_EXCEPTION;

public class PasswordErrorException extends BusinessException {

    public PasswordErrorException() {
        super(PASSWORD_ERROR_EXCEPTION);
    }
}
