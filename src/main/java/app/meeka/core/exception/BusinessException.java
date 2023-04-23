package app.meeka.core.exception;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;

public abstract class BusinessException extends Exception {

    public final String errorCode;
    public final List<Object> args;

    public BusinessException(String errorCode) {
        this(errorCode, emptyList());
    }

    public BusinessException(String errorCode, Object... args) {
        this(errorCode, List.of(args));
    }

    public BusinessException(String errorCode, List<Object> args) {
        this.errorCode = errorCode;
        this.args = unmodifiableList(args);
    }
}
