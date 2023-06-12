package app.meeka.presentation.rest.request;

public record LoginPasswordRequest(
        String email,
        String password
) {
}
