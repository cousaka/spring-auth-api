package com.example.springauthapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * APIエラーのレスポンス
 */
@Getter
@AllArgsConstructor
public class ErrorResponse {
    /** エラーメッセージ */
    private String message;
}
