package com.example.springauthapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * APIエラーのレスポンス
 */
@Getter
@AllArgsConstructor
public class ErrorResponse {

    /** 成功フラグ（常に false） */
    private final boolean success = false;

    /** エラーメッセージ */
    private String message;

    /** 追加情報 */
    private Object data;
}
