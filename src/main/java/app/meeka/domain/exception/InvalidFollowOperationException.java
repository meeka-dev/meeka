package app.meeka.domain.exception;

import app.meeka.core.exception.BusinessException;

import static app.meeka.domain.exception.DomainErrorCodes.INVALID_FOLLOW_OPERATION_EXCEPTION;

public class InvalidFollowOperationException extends BusinessException {
    public InvalidFollowOperationException() {
        super(INVALID_FOLLOW_OPERATION_EXCEPTION);
    }
}
