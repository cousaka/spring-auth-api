package com.example.springauthapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * API成功時レスポンス
 *
 */
@Getter
@AllArgsConstructor
public class SuccessResponse<T> {

    /** 処理が成功した場合 true、失敗した場合 false */
    private boolean success;

    /** 結果メッセージ */
    private String message;

    // 任意のレスポンスデータ
    private T data;
}
