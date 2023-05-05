package app.meeka.application.command;


import lombok.Data;

@Data
public class UserBasicCommand {
    private Long id;
    private String nickName;
    private String icon;


    public UserBasicCommand() {
    }


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
