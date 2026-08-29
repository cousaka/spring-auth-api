package com.example.springauthapi.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springauthapi.domain.Role;
import com.example.springauthapi.domain.User;
import com.example.springauthapi.exception.UserNotFoundException;
import com.example.springauthapi.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * メールアドレスからユーザー情報を取得する
     *
     * @param email メールアドレス
     * @return ユーザー情報
     * @throws UserNotFoundException ユーザーが存在しない場合
     */
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new UserNotFoundException("User not found: " + email));
    }

    /**
     * IDからユーザーを取得する
     *
     * @param id ユーザーID
     * @return ユーザー情報
     * @throws UserNotFoundException ユーザーが存在しない場合
     */
    public User findById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException("ユーザーが見つかりません: " + id));
    }

    /**
     * 全ユーザーを取得する
     *
     * @return ユーザー一覧
     */
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * メールアドレスが既に登録されているか確認する
     *
     * @param email メールアドレス
     * @return 登録済みの場合 true
     */
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * ADMINユーザー数を取得する
     *
     * @return ADMINユーザー数
     */
    public long countAdmins() {
        return userRepository.countByRole(Role.ADMIN);
    }

    /**
     * 新規ユーザーを作成する（登録用）
     *
     * @param email メールアドレス
     * @param encodedPassword ハッシュ化済みパスワード
     * @param name ユーザー名
     * @return 作成されたユーザー
     */
    @Transactional
    public User createUser(String email, String encodedPassword, String name) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(encodedPassword);
        user.setName(name);
        user.setRole(Role.USER);

        return save(user);
    }

    /**
     * ユーザー情報を保存する
     *
     * @param user 保存対象のユーザー
     * @return 保存後のユーザー情報
     */
    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    /**
     * ユーザーを削除する
     *
     * @param user 削除対象ユーザー
     */
    @Transactional
    public void delete(User user) {
        userRepository.delete(user);
    }

    /**
    * ユーザー情報を更新する
    *
    * @param email 認証済みユーザーのメールアドレス
    * @param name 新しいユーザー名
    * @return 更新後のユーザー情報
    */
    @Transactional
    public User update(String email, String name) {
        User user = findByEmail(email);
        user.setName(name);

        return userRepository.save(user);
    }

    /**
     * パスワードを更新する。
     *
     * @param user 対象ユーザー
     * @param encodedPassword ハッシュ化済みパスワード
     * @return 更新後のユーザー情報
     */
    @Transactional
    public User updatePassword(User user, String encodedPassword) {
        user.setPassword(encodedPassword);
        return save(user);
    }

    /**
     * ロールを更新する。
     *
     * @param user 対象ユーザー
     * @param role 新しいロール
     * @return 更新後のユーザー情報
     */
    @Transactional
    public User updateRole(User user, Role role) {
        user.setRole(role);
        return save(user);
    }
}
