package com.example.springauthapi.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.springauthapi.domain.User;
import com.example.springauthapi.repository.UserRepository;

import jakarta.transaction.Transactional;
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
     * @throws UsernameNotFoundException ユーザーが存在しない場合
     */
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
    }

    /**
     * IDからユーザーを取得する
     *
     * @param id ユーザーID
     * @return ユーザー情報
     * @throws UsernameNotFoundException ユーザーが存在しない場合
     */
    public User findById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new UsernameNotFoundException("ユーザーが見つかりません: " + id));
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
}
