package com.example.springauthapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.springauthapi.dto.ErrorResponse;

/**
 * APIで発生する例外を処理するハンドラー
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * メールアドレス重複エラーを処理する
     *
     * @param e メールアドレス重複例外
     * @return エラーレスポンス
     */
    @ExceptionHandler(DuplicateEmailException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDuplicateEmail(DuplicateEmailException e) {
        return new ErrorResponse(e.getMessage());
    }
}
