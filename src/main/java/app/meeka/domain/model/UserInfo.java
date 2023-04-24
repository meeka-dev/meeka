package app.meeka.domain.model;

import java.util.regex.Pattern;

import static java.util.Objects.nonNull;

public record UserInfo (
        String phone,
        String email
){
    public static final String PHONE_REGEX = "^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\\d{8}$";

    public static final String EMAIL_REGEX = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";

    public boolean isValid() {
        return isPhoneValid()||isEmailValid();
    }

    private boolean isPhoneValid() {
        return nonNull(this.phone)&&Pattern.matches(phone,PHONE_REGEX);
    }
    private boolean isEmailValid() {
        return nonNull(this.email)&&Pattern.matches(email,EMAIL_REGEX);
    }
}
