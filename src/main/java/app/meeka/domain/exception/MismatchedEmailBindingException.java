package app.meeka.domain.exception;

import app.meeka.core.exception.BusinessException;

public class MismatchedEmailBindingException extends BusinessException {

    public MismatchedEmailBindingException() {
        super(DomainErrorCodes.MISMATCHED_EMAIL_BINDING_EXCEPTION);
    }
}
