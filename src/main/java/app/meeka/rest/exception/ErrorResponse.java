package app.meeka.rest.exception;

public record ErrorResponse(
        String errorCode,
        String errorMessage
) {
}
