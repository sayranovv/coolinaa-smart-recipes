package com.coolinaa.exception;

/**
 * Исключение, выбрасываемое при некорректных параметрах запроса (HTTP 400).
 * Например, невалидные входные данные, нарушение бизнес-правил.
 */
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
