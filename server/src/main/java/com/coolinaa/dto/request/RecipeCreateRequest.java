package com.coolinaa.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeCreateRequest {

    @NotBlank(message = "title must be not empty")
    @Size(max = 200, message = "title must not exceed 200 characters")
    private String title;

    @Size(max = 1000, message = "description must not exceed 1000 characters")
    private String description;

    @NotBlank(message = "instruction must be not empty")
    private String instruction;

    @Positive(message = "preparation time must me positive number")
    private Integer preparationTime;

    @Positive(message = "cooking time must be positive number")
    private Integer cookingTime;

    @Min(value = 1, message = "difficulty level must be from 1")
    @Max(value = 5, message = "difficulty level must be max 5")
    private Integer difficultyLevel;

    @Positive(message = "servings must be positive number")
    private Integer servings;

    @Size(max = 255, message = "image url must not exceed 255 characters")
    private String imageUrl;

    private Integer categoryId;

    @NotEmpty(message = "ingredients must be not empty")
    private List<RecipeIngredientRequest> ingredients;

    private Boolean isPublic = true;
}
