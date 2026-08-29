package com.example.springauthapi.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.Setter;

/**
 * ユーザー情報を管理するエンティティ
 *
 * usersテーブルに対応する。
 */
@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(name = "uk_users_email", columnNames = "email") })
@Getter
@Setter
public class User {

    /** ユーザーID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** メールアドレス */
    @Column(nullable = false, unique = true)
    private String email;

    /** ハッシュ化されたパスワード */
    @Column(nullable = false)
    private String password;

    /** ユーザー名 */
    @Column(nullable = false)
    private String name;

    /** ユーザー権限 */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    /** 登録日時 */
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /** 更新日時 */
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    /**
     * ユーザー登録前に日時を設定する
     */
    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();

        createdAt = now;
        updatedAt = now;
    }

    /**
     * ユーザー更新前に更新日時を設定する
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
