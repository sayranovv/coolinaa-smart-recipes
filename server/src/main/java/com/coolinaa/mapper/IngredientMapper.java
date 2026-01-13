package com.coolinaa.mapper;

import com.coolinaa.dto.response.IngredientResponse;
import com.coolinaa.entity.Ingredient;

/**
 * Маппер для преобразования сущности {@link Ingredient} в DTO ответа {@link IngredientResponse}.
 * Включает информацию о связанной категории (ID и название).
 */
public final class IngredientMapper {
    private IngredientMapper() {
    }

    public static IngredientResponse toResponse(Ingredient ingredient) {
        if (ingredient == null) {
            return null;
        }
        return IngredientResponse.builder()
                .id(ingredient.getId())
                .name(ingredient.getName())
                .description(ingredient.getDescription())
                .isActive(ingredient.getIsActive())
                .categoryId(ingredient.getCategory() != null ? ingredient.getCategory().getId() : null)
                .categoryName(ingredient.getCategory() != null ? ingredient.getCategory().getName() : null)
                .createdAt(ingredient.getCreatedAt())
                .updatedAt(ingredient.getUpdatedAt())
                .build();
    }
}
