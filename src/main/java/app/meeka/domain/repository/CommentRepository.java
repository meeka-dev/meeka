package app.meeka.domain.repository;

import app.meeka.domain.model.comment.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findByPostIdAndParentIdIsNull(Long postId, Pageable pageable);

    List<Comment> findByParentId(Long parentId, Sort sort);

    Comment findCommentById(Long id);
}
