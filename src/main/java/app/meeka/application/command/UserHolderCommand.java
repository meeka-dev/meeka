package app.meeka.application.command;

import lombok.Data;

@Data
public class UserHolderCommand {
    private Long id;

    public Long getId() {
        return id;
    }
}
