package app.meeka.domain.repository;

import app.meeka.domain.model.user.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    boolean deleteByFollowUserIdAndUserId(Long followUserId, Long userId);

    boolean existsByFollowUserIdAndUserId(Long followUserId, Long userId);

    List<Follow> getFollowsByUserId(Long userId);

    List<Follow> getFollowsByFollowUserId(Long userId);
}
