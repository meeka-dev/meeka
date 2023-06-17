package app.meeka.domain.exception;

import app.meeka.core.exception.BusinessException;

import static app.meeka.domain.exception.DomainErrorCodes.LENGTH_OUT_RANGE_EXCEPTION;

public class LengthOutRangeException extends BusinessException {

    public LengthOutRangeException() {
        super(LENGTH_OUT_RANGE_EXCEPTION);
    }
}
