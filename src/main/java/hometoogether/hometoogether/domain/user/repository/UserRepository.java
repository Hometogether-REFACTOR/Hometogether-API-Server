package hometoogether.hometoogether.domain.user.repository;

import hometoogether.hometoogether.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findByEmail(String email);
    public Optional<User> findUserByUserName(String userName);
    public boolean existsUserByEmail(String email);
    public boolean existsUserByUserName(String userName);
    public void deleteByUserName(String userName);
}
