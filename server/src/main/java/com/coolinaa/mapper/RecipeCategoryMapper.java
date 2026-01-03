package com.coolinaa.mapper;

import com.coolinaa.dto.response.RecipeCategoryResponse;
import com.coolinaa.entity.RecipeCategory;

public final class RecipeCategoryMapper {
    private RecipeCategoryMapper() {}

    public static RecipeCategoryResponse toResponse(RecipeCategory category) {
        if (category == null) {
            return null;
        }
        return RecipeCategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .createdAt(category.getCreatedAt())
                .build();
    }
}
