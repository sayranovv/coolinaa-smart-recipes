package com.coolinaa.mapper;

import com.coolinaa.dto.response.UserResponse;
import com.coolinaa.entity.User;

/**
 * Маппер для преобразования сущности пользователя {@link User} в безопасный DTO {@link UserResponse}.
 * Исключает чувствительные данные, такие как хеш пароля.
 */
public final class UserMapper {
    private UserMapper() {
    }

    public static UserResponse toResponse(User user) {
        if (user == null) {
            return null;
        }
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .isActive(user.getIsActive())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
