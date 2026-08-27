package com.example.springauthapi.exception;

/**
 * 管理者操作で発生した例外
 */
public class AdminOperationException extends RuntimeException {

    /**
     * コンストラクタ
     *
     * @param message エラーメッセージ
     */
    public AdminOperationException(String message) {
        super(message);
    }
}
