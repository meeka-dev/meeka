package app.meeka.domain.model.comment;

import static java.util.Objects.nonNull;

public record CommentInfo(
        Long userId,
        Long postId,
        Long parentId,
        Long answerId,
        String content
) {
    public boolean isValid() {
        return isPostIdValid()
                && isUserIdValid();
    }

    private boolean isUserIdValid() {
        return nonNull(this.userId);
    }

    private boolean isPostIdValid() {
        return nonNull(this.postId);
    }

    public boolean isSizeInRange() {
        return this.content.length() <= 200;
    }

}
