package app.meeka.utils;

import app.meeka.application.command.UserHolderCommand;

public class UserHolder {
    private static final ThreadLocal<UserHolderCommand> tl = new ThreadLocal<>();

    public static void saveUser(UserHolderCommand user){
        tl.set(user);
    }

    public static UserHolderCommand getUser(){
        return tl.get();
    }

    public static void removeUser(){
        tl.remove();
    }
}
