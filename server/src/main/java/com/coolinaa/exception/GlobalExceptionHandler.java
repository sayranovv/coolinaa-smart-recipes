package com.coolinaa.exception;

import com.coolinaa.dto.response.ApiErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;

/**
 * Глобальный обработчик исключений для REST API.
 * <p>
 * Перехватывает исключения, возникающие в контроллерах, и преобразует их в
 * стандартизированный JSON-ответ {@link ApiErrorResponse}.
 * </p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Обработка ошибок валидации аргументов метода (@Valid в контроллерах).
     * Возвращает 400 Bad Request с описанием первой найденной ошибки валидации.
     */
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  org.springframework.http.HttpStatusCode status,
                                                                  WebRequest request) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .orElse("validation error");
        return build(HttpStatus.BAD_REQUEST, message, request);
    }

    /**
     * Обработка ошибок связывания данных (BindException).
     * Аналогично валидации аргументов возвращает 400 Bad Request.
     */
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, org.springframework.http.HttpStatusCode status, WebRequest request) {
        String message = ex.getFieldErrors().stream()
                .findFirst()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .orElse("validation error");
        return build(HttpStatus.BAD_REQUEST, message, request);
    }

    /**
     * Обработка нарушений ограничений БД или валидации параметров методов (@Validated).
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        String message = ex.getConstraintViolations().stream()
                .findFirst()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .orElse("validation error");
        return build(HttpStatus.BAD_REQUEST, message, request);
    }

    /**
     * Обработка исключения {@link NotFoundException} (ресурс не найден).
     * Возвращает 404 Not Found.
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFound(NotFoundException ex, WebRequest request) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    /**
     * Обработка исключения {@link ConflictException} (конфликт данных).
     * Возвращает 409 Conflict.
     */
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Object> handleConflict(ConflictException ex, WebRequest request) {
        return build(HttpStatus.CONFLICT, ex.getMessage(), request);
    }

    /**
     * Обработка общего исключения {@link BadRequestException}.
     * Возвращает 400 Bad Request.
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequest(BadRequestException ex, WebRequest request) {
        return build(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    /**
     * Обработка исключения {@link UnauthorizedException} (ошибка доступа).
     * Возвращает 401 Unauthorized.
     */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Object> handleUnauthorized(UnauthorizedException ex, WebRequest request) {
        return build(HttpStatus.UNAUTHORIZED, ex.getMessage(), request);
    }

    /**
     * Обработчик всех остальных непредвиденных исключений.
     * Логирует ошибку и возвращает 500 Internal Server Error.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneral(Exception ex, WebRequest request) {
        log.error("Unhandled exception", ex);
        return build(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request);
    }

    /**
     * Вспомогательный метод для сборки ответа с ошибкой.
     */
    private ResponseEntity<Object> build(HttpStatus status, String message, WebRequest request) {
        ApiErrorResponse body = ApiErrorResponse.builder()
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .path(request.getDescription(false).replace("uri=", ""))
                .timestamp(OffsetDateTime.now())
                .build();
        return ResponseEntity.status(status).body(body);
    }
}
