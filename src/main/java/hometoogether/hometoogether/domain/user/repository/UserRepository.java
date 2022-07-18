package hometoogether.hometoogether.domain.user.repository;

import hometoogether.hometoogether.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    Optional<User> findUserByUserName(String userName);
    boolean existsUserByEmail(String email);
    boolean existsUserByUserName(String userName);
    void deleteByUserName(String userName);
}
