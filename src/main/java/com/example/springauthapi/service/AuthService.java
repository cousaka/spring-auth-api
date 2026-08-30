package com.example.springauthapi.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.springauthapi.config.JwtUtil;
import com.example.springauthapi.domain.User;
import com.example.springauthapi.dto.LoginData;
import com.example.springauthapi.dto.SuccessResponse;
import com.example.springauthapi.dto.UserResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    /**
     * ユーザーを新規登録する
     *
     * @param email メールアドレス
     * @param password パスワード
     * @param name ユーザー名
     * @return 登録したユーザー情報
     */
    public SuccessResponse<UserResponse> register(String email, String password, String name) {
        // メールアドレスの重複を確認
        if (userService.existsByEmail(email)) {
            return new SuccessResponse<>(false, "このメールアドレスは既に登録されています", null);
        }

        // ユーザー情報を作成・登録
        User saved = userService.createUser(email, passwordEncoder.encode(password), name);

        // ユーザー情報をレスポンスDTOに変換し、返却
        return new SuccessResponse<>(
            true,
            "ユーザー登録が完了しました",
            UserResponse.from(saved, true));
    }

    /**
     * ユーザーをログインさせ、JWTを発行する
     *
     * @param email メールアドレス
     * @param password パスワード
     * @return JWTとユーザー情報
     */
    public SuccessResponse<LoginData> login(String email, String password) {
        // Spring Securityでメールアドレスとパスワードを認証
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        // 認証されたユーザーを取得
        User user = userService.findByEmail(email);
        // 認証成功後にJWTを発行
        String token = jwtUtil.generateToken(email);

        // ユーザー情報をレスポンスDTOに変換し、返却
        LoginData data = new LoginData(token, UserResponse.from(user, true));

        return new SuccessResponse<>(
            true,
            "ログイン成功",
            data);
    }

    /**
     * パスワードを変更する
     *
     * @param email ユーザーのメールアドレス
     * @param currentPassword 現在のパスワード
     * @param newPassword 新しいパスワード
     * @return パスワード変更結果（成功フラグとメッセージ）
     */
    public SuccessResponse<Void> changePassword(String email, String currentPassword, String newPassword) {
        User user = userService.findByEmail(email);

        // 現在のパスワードが一致するか確認
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            return new SuccessResponse<>(false, "現在のパスワードが正しくありません", null);
        }

        // 新しいパスワードをハッシュ化して保存
        userService.updatePassword(user, passwordEncoder.encode(newPassword));

        return new SuccessResponse<>(true, "パスワードを変更しました", null);
    }
}
