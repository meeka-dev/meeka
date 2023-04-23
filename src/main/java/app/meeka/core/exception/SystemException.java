package app.meeka.core.exception;

public abstract class SystemException extends Exception {

    public final Throwable cause;
    public final String errorCode;
    public final String description;

    public SystemException(Throwable cause, String errorCode, String description) {
        this.cause = cause;
        this.errorCode = errorCode;
        this.description = description;
    }

    @Override
    public synchronized Throwable getCause() {
        return super.getCause();
    }
}
