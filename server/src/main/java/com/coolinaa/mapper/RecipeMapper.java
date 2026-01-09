package com.coolinaa.mapper;

import com.coolinaa.dto.response.RecipeIngredientResponse;
import com.coolinaa.dto.response.RecipeResponse;
import com.coolinaa.entity.Recipe;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

public final class RecipeMapper {
    private RecipeMapper() {
    }

    public static RecipeResponse toResponse(Recipe recipe) {
        if (recipe == null) {
            return null;
        }
        List<RecipeIngredientResponse> ingredients = recipe.getIngredients().stream()
                .sorted((a, b) -> Integer.compare(
                        Optional.ofNullable(a.getOrderIndex()).orElse(0),
                        Optional.ofNullable(b.getOrderIndex()).orElse(0)))
                .map(RecipeIngredientMapper::toResponse)
                .collect(Collectors.toList());

        OptionalDouble avg = recipe.getReviews().stream()
                .mapToInt(r -> Optional.ofNullable(r.getRating()).orElse(0))
                .filter(v -> v > 0)
                .average();
        double averageRating = avg.isPresent() ? avg.getAsDouble() : 0.0;
        int reviewCount = (int) recipe.getReviews().stream()
                .filter(r -> r.getRating() != null)
                .count();

        return RecipeResponse.builder()
                .id(recipe.getId())
                .title(recipe.getTitle())
                .description(recipe.getDescription())
                .instructions(recipe.getInstructions())
                .preparationTime(recipe.getPreparationTime())
                .cookingTime(recipe.getCookingTime())
                .difficultyLevel(recipe.getDifficultyLevel())
                .servings(recipe.getServings())
                .imageUrl(recipe.getImageUrl())
                .isPublic(recipe.getIsPublic())
                .status(recipe.getStatus() != null ? recipe.getStatus().getCode() : null)
                .categoryId(recipe.getCategory() != null ? recipe.getCategory().getId() : null)
                .categoryName(recipe.getCategory() != null ? recipe.getCategory().getName() : null)
                .author(UserMapper.toResponse(recipe.getUser()))
                .ingredients(ingredients)
                .averageRating(averageRating)
                .reviewCount(reviewCount)
                .createdAt(recipe.getCreatedAt())
                .updatedAt(recipe.getUpdatedAt())
                .build();
    }
}
