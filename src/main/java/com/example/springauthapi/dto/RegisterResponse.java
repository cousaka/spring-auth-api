package com.example.springauthapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ユーザー登録成功時のレスポンス
 */
@Getter
@AllArgsConstructor
public class RegisterResponse {
    /** 登録されたユーザー情報 */
    private UserResponse user;
}
