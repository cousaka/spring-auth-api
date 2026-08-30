package com.example.springauthapi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springauthapi.domain.User;
import com.example.springauthapi.dto.response.SuccessResponse;
import com.example.springauthapi.dto.response.UserResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserAppService {

    private final UserService userService;

    /**
     * 認証済みユーザーの情報を取得する。
     *
     * @param email 認証済みユーザーのメールアドレス
     * @param authenticated 認証状態（true/false）
     * @return SuccessResponse<UserResponse> ユーザー情報を含むレスポンス
     */
    @Transactional(readOnly = true)
    public SuccessResponse<UserResponse> getMe(String email, boolean authenticated) {
        User user = userService.findByEmail(email);

        return new SuccessResponse<>(
            true,
            "ユーザー情報を取得しました",
            UserResponse.from(user, authenticated));
    }

    /**
     * 認証済みユーザーの情報を更新する。
     *
     * @param email 認証済みユーザーのメールアドレス
     * @param name 新しいユーザー名
     * @param authenticated 認証状態（true/false）
     * @return SuccessResponse<UserResponse> 更新後のユーザー情報
     */
    @Transactional
    public SuccessResponse<UserResponse> updateMe(String email, String name, boolean authenticated) {
        // ユーザー情報を作成
        User user = userService.findByEmail(email);
        user.setName(name);

        // ユーザー情報を更新
        User updated = userService.save(user);

        return new SuccessResponse<>(
            true,
            "ユーザー情報を更新しました",
            UserResponse.from(updated, authenticated));
    }
}
