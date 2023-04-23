package app.meeka.core.rest.exception;

public record ErrorResponse(
        String errorCode,
        String errorMessage
) {
}
