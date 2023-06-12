package app.meeka.domain.exception;

import app.meeka.core.exception.BusinessException;

import static app.meeka.domain.exception.DomainErrorCodes.POST_NOTFOUND_EXCEPTION;

public class PostNotFoundException extends BusinessException {
    public PostNotFoundException() {
        super(POST_NOTFOUND_EXCEPTION);
    }
}
