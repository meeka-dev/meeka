package app.meeka.application.result;

import app.meeka.domain.model.user.User.Gender;

public record PersonalCenterResult(
        String nikeName,
        String icon,
        int fans,
        int followee,
        Gender gender,
        String city,
        String introduction
) {
}
