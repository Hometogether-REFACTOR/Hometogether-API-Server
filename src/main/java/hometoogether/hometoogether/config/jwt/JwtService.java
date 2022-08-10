package hometoogether.hometoogether.config.jwt;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;
    private long accessTokenValidTime = 1000L * 60 * 60;
    private long refreshTokenValidTime = 1000L * 60 * 60 * 24;

    public String generateAccessToken(Long userId) {
        Date now = new Date();
        return Jwts.builder()
                .setIssuedAt(now) // 토큰 발급시간
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenValidTime)) // 토큰 유효시간
                .claim("userId", userId) // 토큰에 담을 데이터
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes()) // secretKey를 사용하여 해싱 암호화 알고리즘 처리
                .compact(); // 직렬화, 문자열로 변경
    }

    public String generateRefreshToken() {
        Date now = new Date();
        return Jwts.builder()
                .setIssuedAt(now) // 토큰 발급시간
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenValidTime)) // 토큰 유효시간
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes()) // secretKey를 사용하여 해싱 암호화 알고리즘 처리
                .compact(); // 직렬화, 문자열로 변경
    }
}
