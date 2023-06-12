package app.meeka.application.command;

public record LoginPasswordCommand(
        String email,
        String password
) {
}
