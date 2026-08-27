package com.example.springauthapi.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * パスワード変更リクエストを表すDTO
 *
 */
@Getter
@Setter
public class ChangePasswordRequest {
    /** 現在のパスワード */
    private String currentPassword;

    /** 新しいパスワード */
    private String newPassword;
}
