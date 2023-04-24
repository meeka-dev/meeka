package app.meeka.presentation.rest.request;

public record CreatePostRequest(
        Long authorId,
        String cover,
        String title,
        String content
) {
}
