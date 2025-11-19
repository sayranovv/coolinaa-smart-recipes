package com.coolinaa.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeMatchResponse {

    private Integer recipeId;
    private String title;
    private String description;
    private String imageUrl;
    private Double matchPercentage;
    private Integer matchedIngredients;
    private Integer totalIngredients;
    private List<MissingIngredientResponse> missingIngredients;
    private Integer cookingTime;
    private Integer difficultyLevel;
    private Double averageRating;

}
