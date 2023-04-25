package app.meeka.domain.model;

import java.util.regex.Pattern;

import static java.util.Objects.nonNull;

public record UserInfo (
        String email
){

    public static final String EMAIL_REGEX = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";

    public boolean isValid() {
        return isEmailValid();
    }

    private boolean isEmailValid() {
        return nonNull(this.email)&&Pattern.matches(email,EMAIL_REGEX);
    }
}
