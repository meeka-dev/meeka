package app.meeka.domain.exception;

import app.meeka.core.exception.BusinessException;

import static app.meeka.domain.exception.DomainErrorCodes.USER_NOT_LOGIN_EXCEPTION;

public class UserNotLoginException extends BusinessException {

    public UserNotLoginException() {
        super(USER_NOT_LOGIN_EXCEPTION);
    }
}
