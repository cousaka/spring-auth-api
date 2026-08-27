package com.example.springauthapi.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.springauthapi.domain.User;
import com.example.springauthapi.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
}
