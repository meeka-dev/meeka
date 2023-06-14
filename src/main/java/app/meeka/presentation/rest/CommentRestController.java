package app.meeka.presentation.rest;


import app.meeka.application.CommentApplicationService;
import app.meeka.application.command.CreateCommentCommand;
import app.meeka.domain.exception.LengthOutRangeException;
import app.meeka.domain.exception.PostNotFoundException;
import app.meeka.domain.model.comment.Comment;
import app.meeka.presentation.rest.request.CreateCommendRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/comment")
public class CommentRestController {

    private final CommentApplicationService commentApplicationService;

    public CommentRestController(CommentApplicationService commentApplicationService) {
        this.commentApplicationService = commentApplicationService;
    }

    @PostMapping("/createComment")
    public void createComment(@RequestBody CreateCommendRequest request) throws PostNotFoundException, LengthOutRangeException {
        CreateCommentCommand command = new CreateCommentCommand(
                request.userId(),
                request.postId(),
                request.parentId(),
                request.answerId(),
                request.content()
        );
        commentApplicationService.createComment(command);
    }

    @PostMapping("/liked")
    public void likedComment(@RequestParam("id") Long commentId, @RequestParam("isLiked") boolean isLiked) {
        commentApplicationService.likedComment(commentId, isLiked);
    }

    @GetMapping("/getPostCommentsByLiked")
    public Page<Comment> getPostCommentsByLiked(Long postId, @PageableDefault(sort = "liked", direction = Sort.Direction.DESC) Pageable pageable) {
        return commentApplicationService.getPostComments(postId, pageable);
    }

    @GetMapping("/getPostCommentsByTime")
    public Page<Comment> getPostCommentsByTime(Long postId, @PageableDefault(sort = "createTime", direction = Sort.Direction.DESC) Pageable pageable) {
        return commentApplicationService.getPostComments(postId, pageable);
    }

    @GetMapping("/getChildCommentsByLiked")
    public List<Comment> getChildCommentsByLiked(@RequestParam("parentId") Long parentId) {
        return commentApplicationService.getChildCommentsByLiked(parentId);
    }

    @GetMapping("/getChildCommentsByTime")
    public List<Comment> getChildCommentsByTime(@RequestParam("parentId") Long parentId) {
        return commentApplicationService.getChildCommentsByTime(parentId);
    }
}
