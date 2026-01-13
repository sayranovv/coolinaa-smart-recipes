package com.coolinaa.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Ответ, возвращаемый при успешной аутентификации или регистрации.
 * Содержит пару токенов (Access + Refresh) и информацию о пользователе.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {

    /**
     * JWT Access-токен для авторизации запросов к защищенным ресурсам.
     */
    private String accessToken;

    /**
     * Тип токена. Обычно "Bearer".
     */
    @Builder.Default
    private String tokenType = "Bearer";

    /**
     * Время жизни Access-токена в миллисекундах.
     */
    private Long expiresIn;

    /**
     * Refresh-токен для обновления Access-токена без повторного ввода пароля.
     */
    private String refreshToken;

    /**
     * Время жизни Refresh-токена в миллисекундах.
     */
    private Long refreshExpiresIn;

    /**
     * Краткая информация о текущем пользователе.
     */
    private UserResponse user;

}
