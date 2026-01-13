package com.coolinaa.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для создания или обновления категории ингредиентов.
 * Используется в административной части для управления справочником (Овощи, Фрукты, Молочные продукты и т.д.).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IngredientCategoryRequest {

    /**
     * Название категории.
     * Обязательное поле. Максимальная длина — 100 символов.
     */
    @NotBlank(message = "name is required")
    @Size(max = 100, message = "name must not exceed 100 characters")
    private String name;

    /**
     * Описание категории.
     * Необязательное поле. Максимальная длина — 255 символов.
     */
    @Size(max = 255, message = "description must not exceed 255 characters")
    private String description;
}
