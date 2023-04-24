package app.meeka.presentation.rest;

import app.meeka.application.PostCommandApplicationService;
import app.meeka.application.command.CreatePostCommand;
import app.meeka.domain.exception.InvalidPostInfoException;
import app.meeka.presentation.rest.request.CreatePostRequest;
import app.meeka.presentation.rest.response.PostCreatedResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post")
public class PostCommandRestController {

    private final PostCommandApplicationService postCommandApplicationService;

    public PostCommandRestController(PostCommandApplicationService postCommandApplicationService) {
        this.postCommandApplicationService = postCommandApplicationService;
    }

    @PostMapping("/create-post")
    public PostCreatedResponse createPost(@RequestBody CreatePostRequest request) throws InvalidPostInfoException {
        var command = new CreatePostCommand(request.authorId(), request.cover(), request.title(), request.content());
        var result = postCommandApplicationService.createPost(command);
        return new PostCreatedResponse(result.postId());
    }
}
