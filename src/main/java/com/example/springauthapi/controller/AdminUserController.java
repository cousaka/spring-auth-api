package com.example.springauthapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springauthapi.dto.RoleUpdateRequest;
import com.example.springauthapi.dto.UserResponse;
import com.example.springauthapi.service.AdminUserService;

/**
 * 管理者向けのユーザー管理APIを提供するコントローラー
 */
@RestController
@RequestMapping("/api/admin/users")
public class AdminUserController {

    private final AdminUserService adminUserService;

    public AdminUserController(AdminUserService adminUserService) {
        this.adminUserService = adminUserService;
    }

    /**
     * 全ユーザーを取得する
     *
     * @return ユーザー一覧
     */
    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll() {
        return ResponseEntity.ok(adminUserService.findAll());
    }

    /**
     * 指定したユーザーを取得する
     *
     * @param id ユーザーID
     * @return ユーザー情報
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
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
    public ResponseEntity<UserResponse> updateRole(
        @PathVariable Long id,
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
    public ResponseEntity<Void> delete(
        @PathVariable Long id,
        org.springframework.security.core.Authentication authentication) {

        adminUserService.delete(id, authentication.getName());

        return ResponseEntity.noContent().build();
    }
}
