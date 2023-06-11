package app.meeka.domain.exception;

import app.meeka.core.exception.BusinessException;

import static app.meeka.domain.exception.DomainErrorCodes.USER_NOT_FOUND_EXCEPTION;

public class UserNotFoundException extends BusinessException {

    public UserNotFoundException(Long userId) {
        super(USER_NOT_FOUND_EXCEPTION, userId);
    }
}
