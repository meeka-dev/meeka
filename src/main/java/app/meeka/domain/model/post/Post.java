package app.meeka.domain.model.post;

import app.meeka.domain.exception.InvalidPostInfoException;
import jakarta.persistence.*;
import org.springframework.data.annotation.PersistenceCreator;

import java.time.OffsetDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;
import static java.time.OffsetDateTime.now;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @SuppressWarnings("unused")
    private Long id;
    private Long authorId;
    private String title;
    private String cover;
    private Integer favours;
    @Enumerated(EnumType.ORDINAL)
    private Status status;
    private String content;
    private OffsetDateTime createdAt;
    private OffsetDateTime lastEditedAt;

    public Post(PostInfo info) throws InvalidPostInfoException {
        if (!info.isValid()) {
            throw new InvalidPostInfoException(info.title());
        }
        this.authorId = info.authorId();
        this.title = info.title();
        this.cover = info.cover();
        this.content = info.content();
        this.status = Status.UNPUBLISHED;
        this.favours = 0;
        this.createdAt = now();
        this.lastEditedAt = this.createdAt;
    }

    @PersistenceCreator
    protected Post() {
    }

    public Status getStatus() {
        return status;
    }

    public enum Status {
        UNPUBLISHED, PUBLIC, PRIVATE
    }

    public PostInfo getInfo() {
        return new PostInfo(this.authorId, this.cover, this.title, this.content);
    }

    public Long getId() {
        return id;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public String getTitle() {
        return title;
    }

    public String getCover() {
        return cover;
    }

    public Integer getFavours() {
        return favours;
    }

    public String getContent() {
        return content;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getLastEditedAt() {
        return lastEditedAt;
    }
}
