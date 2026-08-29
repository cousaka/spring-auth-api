package com.example.springauthapi.config;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    /**
     * JWT認証を行わないAPIを指定する
     *
     * @param request HTTPリクエスト
     * @return JWT認証をスキップする場合はtrue
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();

        return path.equals("/api/auth/login") || path.equals("/api/auth/register");
    }

    /**
     * リクエストごとにJWTを検証する
     *
     * @param request HTTPリクエスト
     * @param response HTTPレスポンス
     * @param filterChain フィルターチェーン
     * @throws ServletException サーブレット処理に失敗した場合
     * @throws IOException 入出力処理に失敗した場合
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        // Authorizationヘッダーを取得
        String header = request.getHeader("Authorization");

        // Authorizationヘッダーなし
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // "Bearer "の後ろからJWTを取得
        String token = header.substring(7);

        try {
            // JWTが無効な場合は認証せずに次の処理へ進む
            if (!jwtUtil.isValid(token)) {
                filterChain.doFilter(request, response);
                return;
            }

            // JWTからメールアドレスを取得
            String email = jwtUtil.extractEmail(token);

            // ユーザー情報を取得
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            // 認証情報を作成
            UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities());

            // SecurityContextに認証情報を設定
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e) {
            // JWT検証などでエラーが発生した場合は認証情報をクリア
            SecurityContextHolder.clearContext();
        }

        // 次のフィルターへ処理を渡す
        filterChain.doFilter(request, response);
    }
}
