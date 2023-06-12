package app.meeka.application.command;


public class UserBasicCommand {
    private Long id;
    private final String nickName;
    private final String icon;

    public UserBasicCommand(Long id, String nickName, String icon) {
        this.id = id;
        this.nickName = nickName;
        this.icon = icon;
    }

    public Long getId() {
        return id;
    }

    public String getNickName() {
        return nickName;
    }

    public String getIcon() {
        return icon;
    }


    public void setId(Long id) {
        this.id = id;
    }
}
