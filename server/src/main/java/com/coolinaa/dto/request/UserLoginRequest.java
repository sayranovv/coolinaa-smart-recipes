package com.coolinaa.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для аутентификации пользователя.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoginRequest {

    /**
     * Логин пользователя: может быть именем пользователя (username) или email.
     * Обязательное поле.
     */
    @NotBlank(message = "email or username required")
    private String emailOrUsername;

    /**
     * Пароль пользователя в открытом виде.
     * Обязательное поле.
     */
    @NotBlank(message = "password must be not empty")
    private String password;

}
