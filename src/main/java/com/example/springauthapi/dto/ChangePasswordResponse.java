package com.example.springauthapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * パスワード変更時のレスポンスを表すDTO
 *
 */
@Getter
@AllArgsConstructor
public class ChangePasswordResponse {

    /** 結果メッセージ */
    private String message;
}
