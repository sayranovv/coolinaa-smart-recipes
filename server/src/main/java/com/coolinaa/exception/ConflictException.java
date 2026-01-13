package com.coolinaa.exception;

/**
 * Исключение, выбрасываемое при конфликте состояния (HTTP 409).
 * Обычно используется при попытке создать дубликат уникальной записи (например, email уже занят).
 */
public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
