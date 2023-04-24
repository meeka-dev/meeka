package app.meeka.domain.exception;

import app.meeka.core.exception.BusinessException;

import static app.meeka.domain.exception.DomainErrorCodes.INVALID_POST_INFO_EXCEPTION;

public class InvalidPostInfoException extends BusinessException {

    public InvalidPostInfoException(String postTitle) {
        super(INVALID_POST_INFO_EXCEPTION, postTitle);
    }
}
