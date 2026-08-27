package com.example.springauthapi.config;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * JWTに関する設定
 *
 * @param secret JWT署名用の秘密鍵
 * @param expiration JWTの有効期限
 */
@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(String secret, Duration expiration) {
}
