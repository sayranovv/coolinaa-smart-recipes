package com.coolinaa.constant;

public class SecurityConstants {
    public static final String TOKEN_TYPE = "Bearer";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    public static final long JWT_EXPIRATION_TIME = 86400000;
    public static final long REFRESH_TOKEN_EXPIRATION_MS = 604800000;

    public static final String SECRET_KEY_PROPERTY = "app.jwt.secret";
    public static final String REFRESH_SECRET_PROPERTY = "app.jwt.refresh-secret";
}
