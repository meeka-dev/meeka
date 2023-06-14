package app.meeka.domain.repository;

import app.meeka.domain.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    User findByEmail(String email);

    User findUserById(Long id);

    User findByIdAndEmail(Long id, String email);

    User findUserByPassword(String password);
}
