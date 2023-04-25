package app.meeka.application.command;

public record CreateUserCommand(
    String account,
    String code
) {
}
