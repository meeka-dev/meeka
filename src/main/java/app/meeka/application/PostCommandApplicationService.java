package app.meeka.application;

import app.meeka.application.command.CreatePostCommand;
import app.meeka.application.result.PostCreatedResult;
import app.meeka.domain.exception.InvalidPostInfoException;
import app.meeka.domain.model.Post;
import app.meeka.domain.model.PostInfo;
import app.meeka.domain.repository.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PostCommandApplicationService {

    private final PostRepository postRepository;

    public PostCommandApplicationService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostCreatedResult createPost(CreatePostCommand command) throws InvalidPostInfoException {
        var post = new Post(new PostInfo(command.authorId(), command.cover(), command.title(), command.content()));
        var savedPost = postRepository.save(post);
        return new PostCreatedResult(savedPost.getId());
    }
}