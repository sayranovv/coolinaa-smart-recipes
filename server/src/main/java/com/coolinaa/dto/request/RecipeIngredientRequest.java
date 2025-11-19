package com.coolinaa.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeIngredientRequest {

    @NotNull(message = "ingredient id is required")
    private Integer ingredientId;

    @NotNull(message = "quantity is required")
    @Positive(message = "quantity must be positive")
    private BigDecimal quantity;

    private Integer unitId;

    @Size(max = 100, message = "notes must not exceed 100 characters")
    private String notes;

    @Min(value = 0, message = "order index must not be negative")
    private Integer orderIndex = 0;

}
