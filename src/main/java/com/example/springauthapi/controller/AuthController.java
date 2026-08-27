package com.example.springauthapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springauthapi.dto.LoginRequest;
import com.example.springauthapi.dto.LoginResponse;
import com.example.springauthapi.dto.RegisterRequest;
import com.example.springauthapi.dto.RegisterResponse;
import com.example.springauthapi.service.AuthService;

/**
 * 認証に関するAPIを提供するコントローラー
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

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
}
