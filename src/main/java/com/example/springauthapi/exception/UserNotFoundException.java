package com.example.springauthapi.exception;

/**
 * ユーザーが存在しない場合に発生する例外
 */
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }
}
