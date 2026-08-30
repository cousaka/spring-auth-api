package com.example.springauthapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springauthapi.dto.ChangePasswordRequest;
import com.example.springauthapi.dto.LoginData;
import com.example.springauthapi.dto.LoginRequest;
import com.example.springauthapi.dto.RegisterRequest;
import com.example.springauthapi.dto.SuccessResponse;
import com.example.springauthapi.dto.UserResponse;
import com.example.springauthapi.service.AuthService;

import lombok.RequiredArgsConstructor;

/**
 * 認証に関するAPIを提供するコントローラー
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * ユーザーを新規登録する
     *
     * @param request ユーザー登録リクエスト
     * @return 登録されたユーザー情報
     */
    @PostMapping("/register")
    public ResponseEntity<SuccessResponse<UserResponse>> register(@RequestBody RegisterRequest request) {
        SuccessResponse<UserResponse> response =
            authService.register(request.getEmail(), request.getPassword(), request.getName());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * ユーザーをログインさせる
     *
     * @param request ログインリクエスト
     * @return JWTとユーザー情報
     */
    @PostMapping("/login")
    public ResponseEntity<SuccessResponse<LoginData>> login(@RequestBody LoginRequest request) {
        SuccessResponse<LoginData> response =
            authService.login(request.getEmail(), request.getPassword());

        return ResponseEntity.ok(response);
    }

    /**
     * パスワードを変更する
     *
     * @param request パスワード変更リクエスト
     * @param authentication ログイン中ユーザー情報（JWTから取得）
     * @return パスワード変更結果
     */
    @PostMapping("/change-password")
    public ResponseEntity<SuccessResponse<Void>> changePassword(@RequestBody ChangePasswordRequest request,
        Authentication authentication) {

        SuccessResponse<Void> response =
            authService.changePassword(authentication.getName(),
                request.getCurrentPassword(),
                request.getNewPassword());

        // 成否に応じて HTTP ステータスを切り替え
        HttpStatus status = response.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status).body(response);
    }

}
