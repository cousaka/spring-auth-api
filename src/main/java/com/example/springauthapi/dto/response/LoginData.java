package com.example.springauthapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ログイン成功時に返却されるデータ
 *
 */
@Getter
@AllArgsConstructor
public class LoginData {

    /** JWT */
    private String token;

    /** ユーザー情報 */
    private UserResponse user;
}
