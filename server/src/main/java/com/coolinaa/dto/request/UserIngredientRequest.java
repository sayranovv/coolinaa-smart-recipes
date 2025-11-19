package com.coolinaa.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class UserIngredientRequest {

    @NotNull(message = "ingredient id is required")
    private Integer ingredientId;

    @NotNull(message = "quantity is required")
    @Positive(message = "quantity must be positive")
    private BigDecimal quantity;

    private Integer unitId;

    private OffsetDateTime expiresAt;
}
