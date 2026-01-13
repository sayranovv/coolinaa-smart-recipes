package com.coolinaa.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

/**
 * Стандартизированный ответ API в случае возникновения ошибки.
 * Возвращается при любых исключительных ситуациях (4xx, 5xx).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiErrorResponse {
    /**
     * HTTP-код статуса (например, 400, 404, 500).
     */
    private int status;

    /**
     * Краткое название ошибки (например, "Not Found", "Bad Request").
     */
    private String error;

    /**
     * Подробное сообщение об ошибке, понятное разработчику клиента.
     */
    private String message;

    /**
     * URI, при обращении к которому произошла ошибка.
     */
    private String path;

    /**
     * Временная метка возникновения ошибки.
     * По умолчанию - текущее время.
     */
    @Builder.Default
    private OffsetDateTime timestamp = OffsetDateTime.now();
}
