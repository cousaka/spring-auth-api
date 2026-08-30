package com.example.springauthapi.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tools.jackson.databind.ObjectMapper;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.example.springauthapi.dto.response.ErrorResponse;

import java.io.IOException;

/**
 * 未認証状態で保護されたAPIにアクセスした場合に呼び出されるエントリーポイント。
 *
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException authException) throws IOException {

        // 401 Unauthorizedを設定
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");

        ErrorResponse error = new ErrorResponse("ログインが必要です", null);

        response.getWriter().write(objectMapper.writeValueAsString(error));
    }
}
