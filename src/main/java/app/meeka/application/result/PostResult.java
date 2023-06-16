package app.meeka.application.result;

import java.time.Duration;

public record PostResult(
        Long authorId,
        String nickName,
        String icon,
        String title,
        String cover,
        String content,
        Integer favours,
        int browse,
        boolean isLiked,
        Duration published
) {
}
