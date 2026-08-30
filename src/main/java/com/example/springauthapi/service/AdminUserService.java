package com.example.springauthapi.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springauthapi.domain.Role;
import com.example.springauthapi.domain.User;
import com.example.springauthapi.dto.SuccessResponse;
import com.example.springauthapi.dto.UserResponse;
import com.example.springauthapi.exception.AdminOperationException;

import lombok.RequiredArgsConstructor;

/**
 * 管理者によるユーザー管理を行うサービス
 */
@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final UserService userService;

    /**
     * 全ユーザーを取得する
     *
     * @return ユーザー一覧
     */
    @Transactional(readOnly = true)
    public SuccessResponse<List<UserResponse>> findAll() {
        var users = userService.findAll()
            .stream()
            .map(user -> UserResponse.from(user, false))
            .toList();

        return new SuccessResponse<>(
            true,
            "ユーザー一覧を取得しました",
            users);
    }

    /**
     * 指定したユーザーを取得する
     *
     * @param id ユーザーID
     * @return ユーザー情報
     */
    @Transactional(readOnly = true)
    public SuccessResponse<UserResponse> findById(Long id) {
        User user = userService.findById(id);

        return new SuccessResponse<>(
            true,
            "ユーザー情報を取得しました",
            UserResponse.from(user, false));
    }

    /**
     * ユーザーのロールを変更する
     *
     * @param id ユーザーID
     * @param role 新しいロール
     * @return 更新されたユーザー情報
     */
    @Transactional
    public SuccessResponse<UserResponse> updateRole(Long id, Role role) {
        User user = userService.findById(id);

        if (role == null) {
            throw new AdminOperationException("ロールを指定してください");
        }

        // 最後のADMINをUSERに変更することを防止
        if (user.getRole() == Role.ADMIN && role == Role.USER && userService.countAdmins() <= 1) {
            throw new AdminOperationException("最後のADMINユーザーをUSERに変更することはできません");
        }

        User updated = userService.updateRole(user, role);

        return new SuccessResponse<>(
            true,
            "ロールを更新しました",
            UserResponse.from(updated, false));
    }

    /**
     * ユーザーを削除する
     *
     * @param id 削除対象ユーザーID
     * @param currentEmail 現在ログインしているADMINのメールアドレス
     */
    @Transactional
    public SuccessResponse<Void> delete(Long id, String currentEmail) {
        User user = userService.findById(id);

        // 自分自身の削除を防止
        if (user.getEmail().equals(currentEmail)) {
            throw new AdminOperationException("自分自身を削除することはできません");
        }

        // 最後のADMINを削除することを防止
        if (user.getRole() == Role.ADMIN && userService.countAdmins() <= 1) {
            throw new AdminOperationException("最後のADMINユーザーを削除することはできません");
        }

        userService.delete(user);

        return new SuccessResponse<>(
            true,
            "ユーザーを削除しました",
            null);
    }
}
