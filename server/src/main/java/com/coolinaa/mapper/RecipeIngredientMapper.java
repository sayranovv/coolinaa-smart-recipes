package com.coolinaa.mapper;

import com.coolinaa.dto.response.RecipeIngredientResponse;
import com.coolinaa.entity.RecipeIngredient;

/**
 * Маппер для преобразования связующей сущности {@link RecipeIngredient} в DTO {@link RecipeIngredientResponse}.
 * Извлекает данные о названии продукта и единице измерения из связанных сущностей.
 */
public final class RecipeIngredientMapper {
    private RecipeIngredientMapper() {
    }

    public static RecipeIngredientResponse toResponse(RecipeIngredient entity) {
        if (entity == null) {
            return null;
        }
        return RecipeIngredientResponse.builder()
                .id(entity.getId())
                .ingredientId(entity.getIngredient() != null ? entity.getIngredient().getId() : null)
                .ingredientName(entity.getIngredient() != null ? entity.getIngredient().getName() : null)
                .quantity(entity.getQuantity())
                .unitId(entity.getUnit() != null ? entity.getUnit().getId() : null)
                .unitName(entity.getUnit() != null ? entity.getUnit().getName() : null)
                .notes(entity.getNotes())
                .orderIndex(entity.getOrderIndex())
                .build();
    }
}
