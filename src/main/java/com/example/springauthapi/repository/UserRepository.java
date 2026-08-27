package com.example.springauthapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springauthapi.domain.Role;
import com.example.springauthapi.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * メールアドレスからユーザーを検索する
     *
     * @param email メールアドレス
     * @return ユーザーが存在する場合はUserを返す
     */
    Optional<User> findByEmail(String email);

    /**
     * メールアドレスが登録済みか確認する
     *
     * @param email メールアドレス
     * @return 登録済みの場合はtrue
     */
    boolean existsByEmail(String email);

    /**
     * 指定したロールのユーザー数を取得する
     *
     * @param role ユーザーロール
     * @return ユーザー数
     */
    long countByRole(Role role);
}
