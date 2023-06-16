package app.meeka.domain.repository;

import app.meeka.domain.model.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Post findPostById(Long id);

    Page<Post> findByAuthorId(Long authorId, Pageable pageable);

}
