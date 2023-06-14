package app.meeka.application.result;

import app.meeka.domain.model.user.User;

import java.time.OffsetDateTime;

public record PersonalCenterResult(
        String nikeName,
        String icon,
        int fans,
        int followee,
        User.Gender gender,
        String city,
        OffsetDateTime birthday,
        String introduce
) {
}
