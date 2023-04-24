package app.meeka.domain.model;

import org.junit.jupiter.api.Test;

import static app.meeka.domain.model.PostInfos.aPostInfo;
import static app.meeka.domain.model.PostInfos.newPostInfo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class PostInfoTest {

    @Test
    void should_return_false_when_judging_post_cover_given_an_invalid_cover_url() {
        var postInfo = newPostInfo().withCover("A invalid post cover url").build();
        assertThat(postInfo.isValid(), is(false));
    }

    @Test
    void should_return_true_when_judging_post_cover_given_a_valid_cover_url() {
        var postInfo = aPostInfo();
        assertThat(postInfo.isValid(), is(true));
    }
}