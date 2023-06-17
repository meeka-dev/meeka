package app.meeka.domain.exception;

import app.meeka.core.exception.BusinessException;

import static app.meeka.domain.exception.DomainErrorCodes.MISMATCHED_EMAIL_BINDING_EXCEPTION;

public class MismatchedEmailBindingException extends BusinessException {

    public MismatchedEmailBindingException() {
        super(MISMATCHED_EMAIL_BINDING_EXCEPTION);
    }
}
