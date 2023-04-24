package app.meeka.domain.matcher;

import app.meeka.domain.model.Post;
import org.hamcrest.CustomMatcher;

import java.util.Objects;

import static java.util.Objects.nonNull;

public class PostMatchers {

    public static CustomMatcher<Post> persisted() {
        return new CustomMatcher<>("persisted") {
            @Override
            public boolean matches(Object actual) {
                return actual instanceof Post actualPost && nonNull(actualPost.getId());
            }
        };
    }

    public static CustomMatcher<Post> sameAs(Post targetPost) {
        return new CustomMatcher<>("same as " + targetPost) {
            @Override
            public boolean matches(Object actual) {
                if (!(actual instanceof Post actualPost)) {
                    return false;
                }
                return Objects.equals(actualPost.getId(), targetPost.getId())
                        && Objects.equals(actualPost.getAuthorId(), targetPost.getAuthorId())
                        && Objects.equals(actualPost.getTitle(), targetPost.getTitle())
                        && Objects.equals(actualPost.getContent(), targetPost.getContent())
                        && Objects.equals(actualPost.getFavours(), targetPost.getFavours())
                        && Objects.equals(actualPost.getCover(), targetPost.getCover())
                        && Objects.equals(actualPost.getCreatedAt(), targetPost.getCreatedAt())
                        && Objects.equals(actualPost.getLastEditedAt(), targetPost.getLastEditedAt())
                        ;
            }
        };
    }
}
