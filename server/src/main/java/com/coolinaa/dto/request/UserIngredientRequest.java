package com.coolinaa.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO для добавления продукта в личный "холодильник" пользователя.
 * Используется для алгоритмов подбора рецептов.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserIngredientRequest {

    /**
     * ID ингредиента из общего справочника.
     * Обязательное поле.
     */
    @NotNull(message = "ingredient id is required")
    private Integer ingredientId;

    /**
     * Наличное количество продукта.
     * Должно быть положительным.
     */
    @NotNull(message = "quantity is required")
    @Positive(message = "quantity must be positive")
    private BigDecimal quantity;

    /**
     * ID единицы измерения количества (г, кг, мл, шт).
     * Если не указано, используется единица по умолчанию.
     */
    private Integer unitId;

    /**
     * Дата истечения срока годности продукта.
     * Полезно для приоритизации рецептов с продуктами, которые скоро испортятся.
     * Может быть null, если срок годности не критичен.
     */
    private LocalDate expiresAt;
}
