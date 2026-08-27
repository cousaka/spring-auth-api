package com.example.springauthapi.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.springauthapi.config.JwtUtil;
import com.example.springauthapi.domain.Role;
import com.example.springauthapi.domain.User;
import com.example.springauthapi.dto.LoginResponse;
import com.example.springauthapi.dto.RegisterResponse;
import com.example.springauthapi.dto.UserResponse;
import com.example.springauthapi.exception.DuplicateEmailException;
import com.example.springauthapi.repository.UserRepository;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthService(
        AuthenticationManager authenticationManager,
        PasswordEncoder passwordEncoder,
        UserRepository userRepository,
        UserService userService,
        JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    /**
     * ユーザーを新規登録する
     *
     * @param email メールアドレス
     * @param password パスワード
     * @param name ユーザー名
     * @return 登録したユーザー情報
     */
    public RegisterResponse register(String email, String password, String name) {
        // メールアドレスの重複を確認
        if (userRepository.existsByEmail(email)) {
            throw new DuplicateEmailException("このメールアドレスは既に登録されています");
        }

        // ユーザー情報を作成
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setName(name);
        user.setRole(Role.USER);

        // ユーザー情報をデータベースに保存
        User saved = userRepository.save(user);

        // ユーザー情報をレスポンスDTOに変換
        UserResponse userResponse = UserResponse.from(saved, true);

        return new RegisterResponse(userResponse);
    }

    /**
     * ユーザーをログインさせ、JWTを発行する
     *
     * @param email メールアドレス
     * @param password パスワード
     * @return JWTとユーザー情報
     */
    public LoginResponse login(String email, String password) {
        // Spring Securityでメールアドレスとパスワードを認証
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        // 認証されたユーザーを取得
        User user = userService.findByEmail(email);

        // 認証成功後にJWTを発行
        String token = jwtUtil.generateToken(email);

        // ユーザー情報をレスポンスDTOに変換
        UserResponse userResponse = UserResponse.from(user, true);

        return new LoginResponse(token, userResponse);
    }

    /**
     * パスワードを変更する
     *
     * @param email ユーザーのメールアドレス
     * @param currentPassword 現在のパスワード
     * @param newPassword 新しいパスワード
     * @return パスワード変更が成功した場合 true、失敗した場合 false
     */
    public boolean changePassword(String email, String currentPassword, String newPassword) {
        User user = userService.findByEmail(email);

        // 現在のパスワードが一致するか確認
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            return false;
        }

        // 新しいパスワードをハッシュ化して保存
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return true;
    }
}
