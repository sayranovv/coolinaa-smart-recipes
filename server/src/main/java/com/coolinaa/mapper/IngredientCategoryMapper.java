package com.coolinaa.mapper;

import com.coolinaa.dto.response.IngredientCategoryResponse;
import com.coolinaa.entity.IngredientCategory;

public final class IngredientCategoryMapper {
    private IngredientCategoryMapper() {
    }

    public static IngredientCategoryResponse toResponse(IngredientCategory category) {
        if (category == null) {
            return null;
        }
        return IngredientCategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .createdAt(category.getCreatedAt())
                .build();
    }
}
