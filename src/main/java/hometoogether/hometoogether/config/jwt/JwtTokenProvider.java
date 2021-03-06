package hometoogether.hometoogether.config.jwt;

import hometoogether.hometoogether.domain.user.domain.User;
import hometoogether.hometoogether.domain.user.repository.UserRepository;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${app.jwtSecret}")
    private String secretKey;
    private long accessTokenValidTime = 1000L * 60 * 60;
    private long refreshTokenValidTime = 1000L * 60 * 60 * 24;

    private final UserRepository userRepository;

    public String generateToken(Long userId) {
        Date now = new Date();
        return Jwts.builder()
                .setIssuedAt(now) // 토큰 발급시간
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenValidTime)) // 토큰 유효시간
                .claim("userId", userId) // 토큰에 담을 데이터
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes()) // secretKey를 사용하여 해싱 암호화 알고리즘 처리
                .compact(); // 직렬화, 문자열로 변경
    }

    public String createRefreshToken() {
        Date now = new Date();
        return Jwts.builder()
                .setIssuedAt(now) // 토큰 발급시간
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenValidTime)) // 토큰 유효시간
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes()) // secretKey를 사용하여 해싱 암호화 알고리즘 처리
                .compact(); // 직렬화, 문자열로 변경
    }

    public boolean isValid(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("토큰이 유효하지 않습니다.");
        }
    }

    public boolean isValidExceptExp(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token);
            return claims.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Long getTokenInfo() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String jwt = request.getHeader("Authorization");
        Jws<Claims> claims = null;
        try {
            claims = Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(jwt); // secretKey를 사용하여 복호화
        } catch (Exception e) {
            throw new RuntimeException("토큰 정보를 불러올 수 없습니다.");
        }
        Object userId = claims.getBody().get("userId");
        return Long.valueOf(userId.toString());
    }

    public User getUserFromJwt() {
        Long userId = this.getTokenInfo();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 유저입니다."));
        return user;
    }
}
