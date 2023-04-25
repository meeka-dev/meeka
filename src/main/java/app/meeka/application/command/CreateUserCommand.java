package app.meeka.application.command;

public record CreateUserCommand(
    String email,
    String code
) {
}
