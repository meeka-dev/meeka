package app.meeka.domain.exception;

import app.meeka.core.exception.BusinessException;

public class PostNotFoundException extends BusinessException {

    public PostNotFoundException() {
        super(DomainErrorCodes.POST_NOT_FOUND_EXCEPTION);
    }
}
