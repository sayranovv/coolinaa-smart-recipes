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

/**
 * Вложенный DTO для привязки ингредиента к рецепту.
 * Описывает, сколько и какого продукта нужно взять.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeIngredientRequest {

    /**
     * ID ингредиента из справочника.
     * Обязательное поле.
     */
    @NotNull(message = "ingredient id is required")
    private Integer ingredientId;

    /**
     * Количество ингредиента.
     * Должно быть положительным числом.
     */
    @NotNull(message = "quantity is required")
    @Positive(message = "quantity must be positive")
    private BigDecimal quantity;

    /**
     * ID единицы измерения (например, граммы, штуки, литры).
     * Если не указано, подразумевается значение по умолчанию для данного ингредиента (обычно штуки).
     */
    private Integer unitId;

    /**
     * Дополнительные заметки к ингредиенту (например, "мелко нарезанный", "комнатной температуры").
     * Максимум 100 символов.
     */
    @Size(max = 100, message = "notes must not exceed 100 characters")
    private String notes;

    /**
     * Порядковый номер ингредиента в списке (для сохранения сортировки при отображении).
     * По умолчанию 0.
     */
    @Min(value = 0, message = "order index must not be negative")
    private Integer orderIndex = 0;

}
