package app.meeka.presentation.rest.request;

import app.meeka.domain.model.post.PostInfo;

public record EditPostInfoRequest(
        Long postId,
        PostInfo editedPostInfo
) {
}
