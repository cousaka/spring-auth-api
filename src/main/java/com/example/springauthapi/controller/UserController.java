package com.example.springauthapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springauthapi.dto.request.UserUpdateRequest;
import com.example.springauthapi.dto.response.SuccessResponse;
import com.example.springauthapi.dto.response.UserResponse;
import com.example.springauthapi.service.UserAppService;

import lombok.RequiredArgsConstructor;

/**
 * ユーザー情報に関するAPIを提供するコントローラー
 */
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserAppService userAppService;

    /**
     * 現在認証されているユーザーの情報を取得する
     *
     * @param authentication Spring Securityの認証情報
     * @return 認証ユーザーの情報
     */
    @GetMapping("/me")
    public ResponseEntity<SuccessResponse<UserResponse>> me(Authentication authentication) {
        var response = userAppService.getMe(authentication.getName(), authentication.isAuthenticated());

        return ResponseEntity.ok(response);
    }

    /**
    * 現在認証されているユーザーの情報を更新する
    *
    * @param authentication Spring Securityの認証情報
    * @param request ユーザー情報更新リクエスト
    * @return 更新後のユーザー情報
    */
    @PutMapping("/me")
    public ResponseEntity<SuccessResponse<UserResponse>> update(Authentication authentication,
        @RequestBody UserUpdateRequest request) {

        var response = userAppService.updateMe(
            authentication.getName(),
            request.getName(),
            authentication.isAuthenticated());

        return ResponseEntity.ok(response);
    }
}
