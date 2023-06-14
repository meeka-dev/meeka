package app.meeka.domain.model.comment;


import app.meeka.domain.exception.LengthOutRangeException;
import app.meeka.domain.exception.PostNotFoundException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.springframework.data.annotation.PersistenceCreator;

import java.time.OffsetDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;
import static java.time.OffsetDateTime.now;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private Long userId;
    private Long postId;
    private Long parentId;
    private Long answerId;
    private String content;
    private int liked;
    private boolean status;
    private OffsetDateTime createdAt;
    private OffsetDateTime lastEditedAt;

    public Comment(CommentInfo info) throws PostNotFoundException, LengthOutRangeException {
        if (!info.isValid()) {
            throw new PostNotFoundException();
        }
        if (!info.isSizeInRange()) {
            throw new LengthOutRangeException();
        }
        this.userId = info.userId();
        this.postId = info.postId();
        this.parentId = null;
        this.answerId = null;
        this.content = info.content();
        this.liked = 0;
        this.status = true;
        this.createdAt = now();
        this.lastEditedAt = this.createdAt;
    }

    @PersistenceCreator
    protected Comment() {

    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getPostId() {
        return postId;
    }

    public Long getParentId() {
        return parentId;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public String getContent() {
        return content;
    }

    public int getLiked() {
        return liked;
    }

    public boolean isStatus() {
        return status;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getLastEditedAt() {
        return lastEditedAt;
    }
}
