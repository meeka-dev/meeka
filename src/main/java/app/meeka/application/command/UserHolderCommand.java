package app.meeka.application.command;


import lombok.Data;

@Data
public class UserHolderCommand {
    private Long id;

    public UserHolderCommand() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
