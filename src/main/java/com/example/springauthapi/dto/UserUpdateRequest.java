package com.example.springauthapi.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * ユーザー情報更新時のリクエスト
 */
@Getter
@Setter
public class UserUpdateRequest {

    /** ユーザー名 */
    private String name;
}
