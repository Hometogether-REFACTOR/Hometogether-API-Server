package hometoogether.hometoogether.domain.user.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserPrincipal implements UserDetails {

    private Long userId;

    private String email;

    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public UserPrincipal(Long id, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.userId = id;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserPrincipal create(User user) {
//        List<GrantedAuthority> authorities = user.getRole().stream().map(role -> new SimpleGrantedAuthority(role.getEmail())).collect(Collectors.toList());
        List<GrantedAuthority> authorities = null;
        return new UserPrincipal(user.getUserId(), user.getEmail(), user.getPassword(), authorities);
    }

    @Override
    public String getPassword() {
        return password;
    }

    public Long getId() {
        return userId;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
