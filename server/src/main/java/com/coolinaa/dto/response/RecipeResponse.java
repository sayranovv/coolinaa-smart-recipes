package com.coolinaa.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeResponse {

    private Integer id;
    private String title;
    private String description;
    private String instructions;
    private Integer preparationTime;
    private Integer cookingTime;
    private Integer difficultyLevel;
    private Integer servings;
    private String imageUrl;
    private Boolean isPublic;
    private String status;
    private Integer categoryId;
    private String categoryName;
    private UserResponse author;
    private List<RecipeIngredientResponse> ingredients;
    private Double averageRating;
    private Integer reviewCount;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

}
