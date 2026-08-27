package com.example.springauthapi.dto;

import com.example.springauthapi.domain.User;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 現在のユーザー情報のレスポンス
 */
@Getter
@AllArgsConstructor
public class UserResponse {
    /** ユーザーID */
    private Long id;

    /** メールアドレス */
    private String email;

    /** ユーザー名 */
    private String name;

    /** ユーザーロール */
    private String role;

    /** 認証済みかどうか */
    private boolean authenticated;

    /**
    * ユーザー情報からレスポンスDTOを生成する
    *
    * @param user ユーザー情報
    * @param authenticated 認証済みかどうか
    * @return ユーザー情報レスポンス
    */
    public static UserResponse from(User user, boolean authenticated) {
        return new UserResponse(
            user.getId(),
            user.getEmail(),
            user.getName(),
            user.getRole().name(),
            authenticated);
    }
}
