package com.example.highschool.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类
 */
@Component
public class JwtTokenUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);

    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";
    private static final String CLAIM_KEY_ROLE = "role";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken(String username, String role) {
        logger.info("Generating token for user: {}, role: {}", username, role);
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, username);
        claims.put(CLAIM_KEY_CREATED, new Date());
        claims.put(CLAIM_KEY_ROLE, role);
        String token = generateToken(claims);
        logger.debug("Generated token: {}", token);
        return token;
    }

    public String getUserNameFromToken(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            return claims.getSubject();
        } catch (Exception e) {
            logger.error("Failed to get username from token", e);
            return null;
        }
    }

    public String getRoleFromToken(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            return (String) claims.get(CLAIM_KEY_ROLE);
        } catch (Exception e) {
            logger.error("Failed to get role from token", e);
            return null;
        }
    }

    public boolean validateToken(String token) {
        logger.debug("Validating token: {}", token);
        boolean valid = !isTokenExpired(token);
        logger.debug("Token validation result: {}", valid);
        return valid;
    }

    private boolean isTokenExpired(String token) {
        Date expiredDate = getExpiredDateFromToken(token);
        return expiredDate.before(new Date());
    }

    private Date getExpiredDateFromToken(String token) {
        return getClaimsFromToken(token).getExpiration();
    }

    private Claims getClaimsFromToken(String token) {
        logger.debug("Parsing token: {}", token);
        try {
            Key key = getSignKey();
            logger.debug("Using signing key: {}", key);
            
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            
            logger.debug("Parsed claims: {}", claims);
            return claims;
        } catch (Exception e) {
            logger.error("Token parsing failed", e);
            throw new IllegalArgumentException("无效的JWT令牌: " + e.getMessage());
        }
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration);
    }

    private String generateToken(Map<String, Object> claims) {
        Key key = getSignKey();
        logger.debug("Generating token with key: {}", key);
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    private final Key signingKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private Key getSignKey() {
        logger.debug("Using signing key: {}", signingKey);
        return signingKey;
    }
}
