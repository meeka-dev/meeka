package app.meeka.application.command;

import app.meeka.domain.model.post.PostInfo;

public record EditPostInfoCommand(
        Long postId,
        PostInfo editedPostInfo
) {
}
