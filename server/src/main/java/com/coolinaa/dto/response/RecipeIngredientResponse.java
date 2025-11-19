package com.coolinaa.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeIngredientResponse {

    private Integer id;
    private Integer ingredientId;
    private String ingredientName;
    private BigDecimal quantity;
    private Integer unitId;
    private String unitName;
    private String notes;
    private Integer orderIndex;

}
