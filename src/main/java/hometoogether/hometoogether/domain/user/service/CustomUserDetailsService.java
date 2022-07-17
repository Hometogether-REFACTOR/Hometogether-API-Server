package hometoogether.hometoogether.domain.user.service;

import hometoogether.hometoogether.domain.user.domain.User;
import hometoogether.hometoogether.domain.user.domain.UserPrincipal;
import hometoogether.hometoogether.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // email == username
        User user = usersRepository.findByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException(username + "is not found.");
        }

        UserPrincipal userPrincipal = UserPrincipal.create(user);

        return userPrincipal;
//        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }

    public UserDetails loadUserById(Long userId) throws UsernameNotFoundException {
        // email == username
        Optional<User> user = usersRepository.findById(userId);

        if (!user.isPresent()) {
            throw new UsernameNotFoundException(userId + "is not found.");
        }

        UserPrincipal userPrincipal = UserPrincipal.create(user.get());

        return userPrincipal;
    }

}
