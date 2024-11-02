package backend.v1.tools;

import backend.v1.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Date;

@Component
@Slf4j
public class JwtTools {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;  // 例如: 86400000 (1天)

    @Value("${jwt.refresh-expiration}")
    private Long refreshExpiration;  // 例如: 604800000 (7天)

    // 生成访问令牌
    public String generateToken(UserPrincipal userPrincipal) {
        return generateToken(userPrincipal, jwtExpiration);
    }

    // 生成刷新令牌
    public String generateRefreshToken(UserPrincipal userPrincipal) {
        return generateToken(userPrincipal, refreshExpiration);
    }

    // 生成令牌的通用方法
    private String generateToken(UserPrincipal userPrincipal, long expiration) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .claim("userId", userPrincipal.getId())
                .claim("roles", userPrincipal.getRoles())
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    // 从token中获取用户名
    public String getUsernameFromToken(String token) {
        Claims claims = parseToken(token);
        return claims.getSubject();
    }

    // 验证token
    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (SecurityException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty");
        }
        return false;
    }

    // 解析token
    private Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 获取签名密钥
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
