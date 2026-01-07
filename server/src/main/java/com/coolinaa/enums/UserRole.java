package com.coolinaa.enums;

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
