package com.example.springauthapi.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.springauthapi.service.security.CustomUserDetailsService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final CustomUserDetailsService userDetailsService;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    /**
     * パスワードエンコーダー
     *
     * @return パスワードエンコーダー
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * AuthenticationManager
     *
     * @param passwordEncoder パスワードエンコーダー
     * @return AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);

        // パスワードの照合に使用するエンコーダーを設定
        provider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(provider);
    }

    /**
     * Security Filter Chain
     *
     * @param http HTTPセキュリティ設定
     * @return SecurityFilterChain
     * @throws Exception セキュリティ設定に失敗した場合
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 別オリジンからのAPIアクセス
            .cors(cors -> {
            })

            // JWT APIなのでCSRF不要（CSRF無効化）
            .csrf(csrf -> csrf.disable())

            // セッションを使わない
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // API の認可設定
            .authorizeHttpRequests(auth -> auth
                // 認証不要 API
                .requestMatchers("/api/auth/register").permitAll()
                .requestMatchers("/api/auth/login").permitAll()

                // ADMIN 専用 API
                .requestMatchers("/api/admin/**").hasRole("ADMIN")

                // その他は JWT 必須
                .anyRequest().authenticated())

            // 未ログイン時のレスポンスをJSONで
            .exceptionHandling(e -> e.authenticationEntryPoint(customAuthenticationEntryPoint))

            // JWT Filter
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * CORS設定
     *
     * @return CORS設定
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // 許可するオリジン
        configuration.setAllowedOrigins(List.of("http://localhost:3000", "http://localhost:5173"));
        // 許可する HTTP メソッド
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // 許可するヘッダー
        configuration.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // 全 API に対して CORS を適用
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
