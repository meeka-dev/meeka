package app.meeka.application.result;

public record UserFollowedResult(
        Long followeeId,
        Boolean followed
) {
}
