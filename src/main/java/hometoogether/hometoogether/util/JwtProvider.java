package hometoogether.hometoogether.util;

import hometoogether.hometoogether.domain.user.domain.BlackList;
import hometoogether.hometoogether.domain.user.domain.RefreshToken;
import hometoogether.hometoogether.domain.user.domain.User;
import hometoogether.hometoogether.domain.user.repository.BlackListRepository;
import hometoogether.hometoogether.domain.user.repository.RefreshTokenRepository;
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
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.blackList}")
    private String blackListPrefix;

    private long accessTokenValidTime = 1000L * 60 * 60;
    private long refreshTokenValidTime = 1000L * 60 * 60 * 24;

    private UserRepository userRepository;
    private RefreshTokenRepository refreshTokenRepository;
    private BlackListRepository blackListRepository;

    public String createAccessToken(Long userId) {
        return createToken(userId, accessTokenValidTime);
    }

    public String createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken(userId.toString(), createToken(userId, refreshTokenValidTime), refreshTokenValidTime);
        refreshTokenRepository.save(refreshToken);
        return refreshToken.getRefreshToken();
    }

    private String createToken(Long userId, Long tokenValidTime) {
        Date now = new Date();
        return Jwts.builder()
                .setIssuedAt(now) // 토큰 발급시간
                .setExpiration(new Date(System.currentTimeMillis() + tokenValidTime)) // 토큰 유효시간
                .claim("userId", userId) // 토큰에 담을 데이터
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes()) // secretKey를 사용하여 해싱 암호화 알고리즘 처리
                .compact(); // 직렬화, 문자열로 변경
    }

    public void blackListToken(Long userId, String accessToken) {
        BlackList blackList = new BlackList(userId.toString(), accessToken, accessTokenValidTime);
        refreshTokenRepository.deleteById(userId.toString());
        blackListRepository.save(blackList);
    }

    public boolean validateAccessToken(String accessToken) {
        try {
            Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(accessToken);
        } catch (SignatureException ex) {
            throw new RuntimeException("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            throw new RuntimeException("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new RuntimeException("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new RuntimeException("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException("JWT claims string is empty.");
        }
        return true;
    }

    public void validateRefreshToken(String userId, String refreshToken) {
        RefreshToken cachedToken = refreshTokenRepository.findById(userId).orElseThrow(() -> new RuntimeException());
        if (!refreshToken.equals(cachedToken.getRefreshToken())) {
            throw new RuntimeException("Refresh 토큰이 만료되었습니다.");
        }
    }

    public User getUserFromHeader() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String userId = request.getHeader("userId");
        User user = userRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new RuntimeException("존재하지 않는 유저입니다."));
        return user;
    }
}
