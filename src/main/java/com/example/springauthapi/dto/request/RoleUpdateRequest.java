package com.example.springauthapi.dto.request;

import com.example.springauthapi.domain.Role;

import lombok.Getter;
import lombok.Setter;

/**
 * ユーザーロール変更時のリクエスト
 */
@Getter
@Setter
public class RoleUpdateRequest {

    /** ユーザーロール */
    private Role role;
}
