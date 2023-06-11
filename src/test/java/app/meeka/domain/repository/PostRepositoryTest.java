package app.meeka.domain.repository;

import app.meeka.domain.exception.InvalidPostInfoException;
import app.meeka.domain.model.post.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static app.meeka.domain.model.PostInfos.aPostInfo;
import static app.meeka.domain.assertion.PostMatchers.persisted;
import static app.meeka.domain.assertion.PostMatchers.sameAs;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    void should_generate_id_when_save_post() throws InvalidPostInfoException {
        var post = new Post(aPostInfo());
        var savedPost = postRepository.save(post);
        assertThat(savedPost, is(persisted()));
        assertThat(savedPost, is(sameAs(post)));
    }
}
