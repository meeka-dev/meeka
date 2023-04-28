package app.meeka.application.result;

import app.meeka.domain.model.User;

public record PersonalCenterResult(
    String nikeName,
    String icon,
    int fans,
    int followee,
    User.Gender gender,
    String city,
    String introduce
) {
}
