package org.example.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JwtUtil {
    
    @Value("${jwt.secret}")
    private String secretKey;
    
    @Value("${jwt.expiration}")
    private long expirationTime;
    
    @Value("${jwt.refresh-threshold}")
    private long refreshThreshold;
    
    private SecretKey key;
    
    // 初始化密钥
    private SecretKey getKey() {
        if (key == null) {
            key = Keys.hmacShaKeyFor(secretKey.getBytes());
        }
        return key;
    }
    
    /**
     * 生成JWT Token
     * @param userId 用户ID
     * @param userType 用户类型 (student/admin)
     * @param username 用户名
     * @return JWT Token
     */
    public String generateToken(String userId, String userType, String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("userType", userType);
        claims.put("username", username);
        
        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
        
        log.info("生成JWT Token成功，用户ID: {}, 用户类型: {}", userId, userType);
        return token;
    }
    
    /**
     * 验证JWT Token
     * @param token JWT Token
     * @return 是否有效
     */
    public boolean validateToken(String token) {
        try {
            log.debug("开始验证token: {}", token.substring(0, Math.min(20, token.length())) + "...");
            Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token);
            log.debug("Token验证成功");
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.warn("Token验证失败: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * 从Token中获取用户ID
     * @param token JWT Token
     * @return 用户ID
     */
    public String getUserIdFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.get("userId", String.class);
    }
    
    /**
     * 从Token中获取用户类型
     * @param token JWT Token
     * @return 用户类型
     */
    public String getUserTypeFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.get("userType", String.class);
    }
    
    /**
     * 从Token中获取用户名
     * @param token JWT Token
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.get("username", String.class);
    }
    
    /**
     * 从Token中获取过期时间
     * @param token JWT Token
     * @return 过期时间
     */
    public Date getExpirationFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }
    
    /**
     * 检查Token是否即将过期
     * @param token JWT Token
     * @return 是否即将过期
     */
    public boolean isTokenNearExpiration(String token) {
        Date expiration = getExpirationFromToken(token);
        long currentTime = System.currentTimeMillis();
        long expirationTime = expiration.getTime();
        boolean isNearExpiration = (expirationTime - currentTime) < refreshThreshold;
        log.debug("Token即将过期检查: 当前时间={}, 过期时间={}, 是否即将过期={}", 
                 currentTime, expirationTime, isNearExpiration);
        return isNearExpiration;
    }
    
    /**
     * 从Token中获取Claims
     * @param token JWT Token
     * @return Claims对象
     */
    private Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    
    /**
     * 刷新Token（生成新的Token，保持相同的用户信息）
     * @param token 原Token
     * @return 新的Token
     */
    public String refreshToken(String token) {
        Claims claims = getClaimsFromToken(token);
        String userId = claims.get("userId", String.class);
        String userType = claims.get("userType", String.class);
        String username = claims.get("username", String.class);
        
        log.info("刷新Token，用户ID: {}, 用户类型: {}", userId, userType);
        return generateToken(userId, userType, username);
    }
} 