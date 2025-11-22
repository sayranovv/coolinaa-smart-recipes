package com.coolinaa.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.refresh-secret}")
    private String refreshSecret;

    @Value("${app.jwt.expiration-ms}")
    private long jwtExpirationMs;

    @Value("${app.jwt.refresh-expiration-ms}")
    private long refreshExpirationMs;

    private SecretKey getSigningKey(String secret) {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }


    public String generateAccessToken(Authentication authentication) {
        String username = authentication.getName();
        String role = authentication.getAuthorities().stream()
                .findFirst()
                .map(Object::toString)
                .orElse("ROLE_USER");

        return buildToken(username, role, jwtSecret, jwtExpirationMs);
    }

    public String generateAccessToken(String username, String role) {
        return buildToken(username, role, jwtSecret, jwtExpirationMs);
    }

    public String generateRefreshToken(String username) {
        return buildToken(username, "REFRESH", refreshSecret, refreshExpirationMs);
    }

    private String buildToken(String username, String role, String secret, long expirationMs) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(secret), SignatureAlgorithm.HS512)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return parseClaims(token, jwtSecret).getSubject();
    }

    public String getRoleFromToken(String token) {
        return parseClaims(token, jwtSecret).get("role", String.class);
    }

    public String getUsernameFromRefreshToken(String token) {
        return parseClaims(token, refreshSecret).getSubject();
    }

    public boolean validateAccessToken(String token) {
        return validateToken(token, jwtSecret);
    }

    public boolean validateRefreshToken(String token) {
        return validateToken(token, refreshSecret);
    }

    public boolean validateToken(String token, String secret) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey(secret))
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SecurityException ex) {
            log.error("invalid jwt signature: {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            log.error("invalid jwt token: {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            log.error("expired jwt token: {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            log.error("unsupported jwt token: {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.error("jwt claims string is empty: {}", ex.getMessage());
        }
        return false;
    }

    public long getExpirationTimeFromToken(String token) {
        return parseClaims(token, jwtSecret).getExpiration().getTime();
    }

    private Claims parseClaims(String token, String secret) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(secret))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
