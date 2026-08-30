package com.example.springauthapi.dto.request;

import lombok.Getter;
import lombok.Setter;

/**
 * ログイン時のリクエスト
 */
@Getter
@Setter
public class LoginRequest {

    /** メールアドレス */
    private String email;

    /** パスワード */
    private String password;
}
