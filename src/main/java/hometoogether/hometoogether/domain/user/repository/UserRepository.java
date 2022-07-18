package hometoogether.hometoogether.domain.user.repository;

import hometoogether.hometoogether.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
//    Optional<User> findUserByUserName(String userName);
//    boolean existsUserByUserName(String userName);
}
