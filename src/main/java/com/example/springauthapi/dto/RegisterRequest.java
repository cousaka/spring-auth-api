package com.example.springauthapi.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * ユーザー登録時のリクエスト
 */
@Getter
@Setter
public class RegisterRequest {

    /** メールアドレス */
    private String email;

    /** パスワード */
    private String password;

    /** ユーザー名 */
    private String name;
}
