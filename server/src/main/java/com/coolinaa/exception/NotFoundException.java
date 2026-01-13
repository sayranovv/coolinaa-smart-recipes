package com.coolinaa.exception;

/**
 * Исключение, выбрасываемое, если запрашиваемый ресурс не найден (HTTP 404).
 * Например, рецепт или пользователь с указанным ID не существует.
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
