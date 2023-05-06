package app.meeka.utils;

public class RedisConstants {

    public static final String LOGIN_CODE_KEY = "login:code:";
    public static final Long LOGIN_CODE_TTL = 5L;
    public static final String LOGIN_USER_KEY = "login:user:";
    public static final Long LOGIN_USER_TTL = 30L;
    public static final String FOLLOW_KEY = "follows:";
    public static final String UPDATE_PASSWORD_CODE_KEY = "password:code:";
    public static final Long UPDATE_PASSWORD_CODE_TTL = 5L;
}
