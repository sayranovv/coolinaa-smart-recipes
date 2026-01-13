package com.coolinaa.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Информация об отсутствующем ингредиенте для приготовления рецепта.
 * Используется в алгоритме подбора рецептов ("Что приготовить").
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MissingIngredientResponse {

    private Integer ingredientId;
    private String ingredientName;
    private BigDecimal quantity;
    private String unitName;

}
