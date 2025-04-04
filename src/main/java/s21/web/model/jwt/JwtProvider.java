package s21.web.model.jwt;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import s21.web.model.UserDTO;

@Component
public class JwtProvider {
    private final SecretKey jwtAccessSecret;

    public  JwtProvider(
        @Value("${jwt.secret.access}") String jwtAccessSecret
    ) {
        this.jwtAccessSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessSecret));
    }


    public String generateAccessToken(UserDTO user) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant accessExpirationInstant = now.plusDays(7).atZone(ZoneId.systemDefault()).toInstant();
        final Date accessExpiration = Date.from(accessExpirationInstant);

        return Jwts.builder()
                .subject(user.getUsername())
                .expiration(accessExpiration)
                .signWith(jwtAccessSecret)
                .claim("uuid", user.getUuid())
                .claim("roles", user.getAuthorities())
                .compact();
    }

    public boolean validateAccessToken(String accessToken) {
        return validateToken(accessToken, jwtAccessSecret);
    }

    @SuppressWarnings("UseSpecificCatch")
    private boolean validateToken(String token, SecretKey secret) {
        try {
            Jwts.parser()
                    .verifyWith(secret)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        return false;
    }
    public Claims getAccessClaims(String token) {
        return getClaims(token, jwtAccessSecret);
    }
    private Claims getClaims(String token, SecretKey secret) {
        return Jwts.parser()
                        .verifyWith(secret)
                        .build()
                        .parseSignedClaims(token)
                        .getPayload();
    }
}
