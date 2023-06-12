package app.meeka.application.result;

import app.meeka.application.command.UserBasicCommand;

import java.util.List;

public record UserListResult(
        List<UserBasicCommand> list
) {
}
