package com.coolinaa.enums;

/**
 * Роли пользователей в системе.
 * Определяют права доступа (Authorities) для Spring Security.
 */
public enum UserRole {
    user("ROLE_USER"),
    admin("ROLE_ADMIN");

    private final String authority;

    UserRole(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }
}
