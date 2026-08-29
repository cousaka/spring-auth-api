package com.example.springauthapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springauthapi.domain.User;
import com.example.springauthapi.dto.UserResponse;
import com.example.springauthapi.dto.UserUpdateRequest;
import com.example.springauthapi.service.UserService;

import lombok.RequiredArgsConstructor;

/**
 * ユーザー情報に関するAPIを提供するコントローラー
 */
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

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

    /**
    * 現在認証されているユーザーの情報を更新する
    *
    * @param authentication Spring Securityの認証情報
    * @param request ユーザー情報更新リクエスト
    * @return 更新後のユーザー情報
    */
    @PutMapping("/me")
    public ResponseEntity<UserResponse> update(Authentication authentication, @RequestBody UserUpdateRequest request) {

        User user = userService.update(authentication.getName(), request.getName());

        UserResponse response = UserResponse.from(user, authentication.isAuthenticated());

        return ResponseEntity.ok(response);
    }
}
