package app.meeka.application.command;

public record CreateCommentCommand(
        Long userId,
        Long postId,
        Long parentId,
        Long answerId,
        String content
) {
}
