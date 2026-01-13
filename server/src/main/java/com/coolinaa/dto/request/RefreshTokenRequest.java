package com.coolinaa.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO запроса для обновления Access-токена.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshTokenRequest {
    /**
     * Действующий Refresh-токен, полученный при входе.
     * Обязательное поле.
     */
    @NotBlank(message = "refresh token is required")
    private String refreshToken;
}
