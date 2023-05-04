package app.meeka.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.OffsetDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;
import static java.time.OffsetDateTime.now;


@Entity
public class Follow {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @SuppressWarnings("unused")
    private Long id;
    private Long userId;
    private Long followUserId;
    private OffsetDateTime createTime;

    public Follow(Long userId, Long followId) {
        this.userId = userId;
        this.followUserId = followId;
        this.createTime = now();
    }

    public Follow() {

    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getFollowId() {
        return followUserId;
    }

    public OffsetDateTime getCreateTime() {
        return createTime;
    }
}
