package com.example.springauthapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.springauthapi.dto.response.ErrorResponse;

/**
 * API全体の例外を処理するハンドラー
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * ユーザーが存在しない場合
     *
     * @param e 例外
     * @return HTTP 404
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException e) {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponse(e.getMessage(), null));
    }

    /**
     * ADMIN操作に失敗した場合
     *
     * @param e 例外
     * @return HTTP 400
     */
    @ExceptionHandler(AdminOperationException.class)
    public ResponseEntity<ErrorResponse> handleAdminOperation(AdminOperationException e) {
        return ResponseEntity
            .badRequest()
            .body(new ErrorResponse(e.getMessage(), null));
    }

    /**
     * ログイン認証に失敗した場合
     *
     * @param e 例外
     * @return HTTP 401
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException e) {
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(new ErrorResponse("メールアドレスまたはパスワードが正しくありません", null));
    }

    /**
     * その他の予期しない例外（500）
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ErrorResponse("サーバー内部でエラーが発生しました", null));
    }
}
