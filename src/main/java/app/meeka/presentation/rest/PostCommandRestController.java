package app.meeka.presentation.rest;

import app.meeka.application.PostCommandApplicationService;
import app.meeka.application.command.CreatePostCommand;
import app.meeka.application.command.DeletePostCommand;
import app.meeka.application.command.EditPostInfoCommand;
import app.meeka.application.result.PostResult;
import app.meeka.domain.exception.InvalidPostInfoException;
import app.meeka.presentation.rest.request.CreatePostRequest;
import app.meeka.presentation.rest.request.DeletePostRequest;
import app.meeka.presentation.rest.request.EditPostInfoRequest;
import app.meeka.presentation.rest.response.PostCreatedResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/delete-post")
    public void deletePost(DeletePostRequest request) {
        var command = new DeletePostCommand(request.postId());
        postCommandApplicationService.deletePost(command);
    }

    @PostMapping("/edit-post-info")
    public void editPostInfo(EditPostInfoRequest request) {
        var command = new EditPostInfoCommand(request.postId(), request.editedPostInfo());
        postCommandApplicationService.editPostInfo(command);
    }

    @PostMapping("/query-posts-by-author-id")
    public List<PostResult> getPostByAuthorId(@RequestParam Long authorId, @PageableDefault(sort = "createTime", direction = Sort.Direction.DESC) Pageable pageable) {
        return postCommandApplicationService.getPostByAuthorId(authorId, pageable);
    }

    @PostMapping("/query-post-by-id")
    public PostResult getPostByPostId(@RequestParam Long postId) {
        return postCommandApplicationService.getPostByPostId(postId);
    }
}
