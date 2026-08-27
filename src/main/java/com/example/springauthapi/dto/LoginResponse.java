package com.example.springauthapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ログイン成功時のレスポンス
 */
@Getter
@AllArgsConstructor
public class LoginResponse {
    /** JWT */
    private String token;

    /** ユーザー情報 */
    private UserResponse user;
}
