package app.meeka.domain.repository;

import app.meeka.domain.model.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow,Long> {
    boolean deleteByFollowIdAndUserId(Long followId,Long userId);
    boolean existsByFollowIdAndUserId(Long followId,Long userId);
}
