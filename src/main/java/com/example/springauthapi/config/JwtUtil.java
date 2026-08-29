package com.example.springauthapi.config;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final JwtProperties jwtProperties;

    /**
     * JWT署名用のSecretKeyを生成する
     *
     * @return JWT署名用のSecretKey
     */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtProperties.secret().getBytes(StandardCharsets.UTF_8));
    }

    /**
     * JWTを生成する
     *
     * @param email ユーザーのメールアドレス
     * @return 生成されたJWT
     */
    public String generateToken(String email) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + jwtProperties.expiration().toMillis());

        return Jwts.builder()
            .claims()
            .subject(email)
            .issuedAt(now)
            .expiration(expiration)
            .and()
            .signWith(getSigningKey())
            .compact();
    }

    /**
     * JWTからメールアドレスを取得する
     *
     * @param token JWT
     * @return メールアドレス
     */
    public String extractEmail(String token) {
        Claims claims = Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();

        return claims.getSubject();
    }

    /**
     * JWTの有効性を検証する
     *
     * @param token JWT
     * @return 有効な場合はtrue、無効な場合はfalse
     */
    public boolean isValid(String token) {
        try {
            Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token);

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
