package com.coolinaa.dto.response;

import com.coolinaa.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

/**
 * Публичная информация о пользователе.
 * Не содержит паролей и других чувствительных данных.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private Integer id;
    private String username;
    private String email;
    private Boolean isActive;
    private UserRole role;
    private OffsetDateTime createdAt;

}
