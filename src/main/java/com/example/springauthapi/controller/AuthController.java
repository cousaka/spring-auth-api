package com.example.springauthapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springauthapi.dto.ChangePasswordRequest;
import com.example.springauthapi.dto.ChangePasswordResponse;
import com.example.springauthapi.dto.LoginRequest;
import com.example.springauthapi.dto.LoginResponse;
import com.example.springauthapi.dto.RegisterRequest;
import com.example.springauthapi.dto.RegisterResponse;
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
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        RegisterResponse response = authService.register(request.getEmail(), request.getPassword(), request.getName());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * ユーザーをログインさせる
     *
     * @param request ログインリクエスト
     * @return JWTとユーザー情報
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request.getEmail(), request.getPassword());

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
    public ResponseEntity<ChangePasswordResponse> changePassword(@RequestBody ChangePasswordRequest request,
        Authentication authentication) {
        String email = authentication.getName();

        boolean success = authService.changePassword(email, request.getCurrentPassword(), request.getNewPassword());

        if (!success) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ChangePasswordResponse("現在のパスワードが正しくありません"));
        }

        return ResponseEntity.ok(new ChangePasswordResponse("パスワードを変更しました"));
    }
}
