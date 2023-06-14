package app.meeka.presentation.rest.request;

public record CreateCommendRequest(
        Long userId,
        Long postId,
        Long parentId,
        Long answerId,
        String content
) {
}
