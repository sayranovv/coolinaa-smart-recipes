package com.coolinaa.mapper;

import com.coolinaa.dto.response.UserIngredientResponse;
import com.coolinaa.entity.UserIngredient;

import java.time.OffsetDateTime;

/**
 * Маппер для преобразования сущности "Холодильник пользователя" {@link UserIngredient} в DTO.
 * <p>
 * Дополнительно вычисляет статус просроченности продукта (isExpired) на основе текущей даты.
 * </p>
 */
public final class UserIngredientMapper {
    private UserIngredientMapper() {
    }

    public static UserIngredientResponse toResponse(UserIngredient entity) {
        if (entity == null) {
            return null;
        }
        OffsetDateTime now = OffsetDateTime.now();
        boolean expired = entity.getExpiresAt() != null && entity.getExpiresAt().isBefore(now);
        return UserIngredientResponse.builder()
                .id(entity.getId())
                .ingredientId(entity.getIngredient() != null ? entity.getIngredient().getId() : null)
                .ingredientName(entity.getIngredient() != null ? entity.getIngredient().getName() : null)
                .quantity(entity.getQuantity())
                .unitId(entity.getUnit() != null ? entity.getUnit().getId() : null)
                .unitName(entity.getUnit() != null ? entity.getUnit().getName() : null)
                .addedAt(entity.getAddedAt())
                .expiresAt(entity.getExpiresAt())
                .isExpired(expired)
                .build();
    }
}
