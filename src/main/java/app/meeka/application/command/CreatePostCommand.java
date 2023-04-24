package app.meeka.application.command;

public record CreatePostCommand(
        Long authorId,
        String cover,
        String title,
        String content
) {
}
