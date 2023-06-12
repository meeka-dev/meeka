package app.meeka.core.context;

import app.meeka.application.command.UserBasicCommand;

public class UserHolder {
    private static final ThreadLocal<UserBasicCommand> tl = new ThreadLocal<>();

    public static void saveUser(UserBasicCommand user) {
        tl.set(user);
    }

    public static UserBasicCommand getUser() {
        return tl.get();
    }

    public static void removeUser(){
        tl.remove();
    }
}
