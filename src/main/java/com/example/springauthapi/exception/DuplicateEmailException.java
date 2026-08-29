package com.example.springauthapi.exception;

/**
 * メールアドレスが重複した場合に発生する例外
 */
public class DuplicateEmailException extends RuntimeException {

    public DuplicateEmailException(String message) {
        super(message);
    }
}
