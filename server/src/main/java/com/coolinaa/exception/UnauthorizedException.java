package com.coolinaa.exception;

/**
 * Исключение, указывающее на проблемы с аутентификацией (HTTP 401).
 * Выбрасывается, когда пользователь не залогинен или токен недействителен.
 */
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
