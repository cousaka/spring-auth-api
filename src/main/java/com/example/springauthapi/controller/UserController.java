package com.example.springauthapi.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springauthapi.domain.User;
import com.example.springauthapi.dto.UserResponse;
import com.example.springauthapi.service.UserService;

/**
 * ユーザー情報に関するAPIを提供するコントローラー
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 現在認証されているユーザーの情報を取得する
     *
     * @param authentication Spring Securityの認証情報
     * @return 認証ユーザーの情報
     */
    @GetMapping("/me")
    public UserResponse me(Authentication authentication) {
        // 認証情報からメールアドレスを取得
        String email = authentication.getName();

        // ユーザー情報を取得
        User user = userService.findByEmail(email);

        // レスポンスDTOに変換
        return UserResponse.from(user, authentication.isAuthenticated());
    }
}
