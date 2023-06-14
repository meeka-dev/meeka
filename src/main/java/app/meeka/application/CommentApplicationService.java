package app.meeka.application;

import app.meeka.application.command.CreateCommentCommand;
import app.meeka.domain.exception.LengthOutRangeException;
import app.meeka.domain.exception.PostNotFoundException;
import app.meeka.domain.model.comment.Comment;
import app.meeka.domain.model.comment.CommentInfo;
import app.meeka.domain.repository.CommentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CommentApplicationService {

    private final CommentRepository commentRepository;

    public CommentApplicationService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    //keypoint: 分页获取文章一级评论
    public Page<Comment> getPostComments(Long postId, Pageable pageable) {
        return commentRepository.findByPostIdAndParentIdIsNull(postId, pageable);
    }

    //keypoint: 按点赞量获取评论的子评论
    public List<Comment> getChildCommentsByLiked(Long parentId) {
        Sort sort = Sort.by(Sort.Direction.DESC, "liked");
        return commentRepository.findByParentId(parentId, sort);
    }

    //keypoint: 按发布时间获取评论的子评论
    public List<Comment> getChildCommentsByTime(Long parentId) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        return commentRepository.findByParentId(parentId, sort);
    }

    //keypoint: 发布评论
    public void createComment(CreateCommentCommand commentCommand) throws PostNotFoundException, LengthOutRangeException {
        CommentInfo info = new CommentInfo(
                commentCommand.userId(),
                commentCommand.postId(),
                commentCommand.parentId(),
                commentCommand.answerId(),
                commentCommand.content()
        );
        Comment comment = new Comment(info);
        commentRepository.save(comment);
    }

    //keypoint: 点赞评论
    public void likedComment(Long commentId, boolean isLiked) {
        Comment comment = commentRepository.findCommentById(commentId);
        comment.updateLiked(isLiked);
        commentRepository.save(comment);
    }
}
