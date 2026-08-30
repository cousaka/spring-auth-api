package com.example.springauthapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springauthapi.dto.RoleUpdateRequest;
import com.example.springauthapi.dto.SuccessResponse;
import com.example.springauthapi.dto.UserResponse;
import com.example.springauthapi.service.AdminUserService;

import lombok.RequiredArgsConstructor;

/**
 * 管理者向けのユーザー管理APIを提供するコントローラー
 */
@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final AdminUserService adminUserService;

    /**
     * 全ユーザーを取得する
     *
     * @return ユーザー一覧
     */
    @GetMapping
    public ResponseEntity<SuccessResponse<?>> findAll() {
        return ResponseEntity.ok(adminUserService.findAll());
    }

    /**
     * 指定したユーザーを取得する
     *
     * @param id ユーザーID
     * @return ユーザー情報
     */
    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse<UserResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(adminUserService.findById(id));
    }

    /**
     * 指定したユーザーのロールを変更する
     *
     * @param id ユーザーID
     * @param request ロール変更リクエスト
     * @return 更新されたユーザー情報
     */
    @PutMapping("/{id}/role")
    public ResponseEntity<SuccessResponse<UserResponse>> updateRole(@PathVariable Long id,
        @RequestBody RoleUpdateRequest request) {
        return ResponseEntity.ok(adminUserService.updateRole(id, request.getRole()));
    }

    /**
     * 指定したユーザーを削除する
     *
     * @param id ユーザーID
     * @return HTTP 204 No Content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse<Void>> delete(@PathVariable Long id, Authentication authentication) {
        var response = adminUserService.delete(id, authentication.getName());

        return ResponseEntity.ok(response);
    }
}
