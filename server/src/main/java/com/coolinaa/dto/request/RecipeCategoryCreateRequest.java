package com.coolinaa.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для создания или обновления категории рецептов.
 * Примеры: "Завтраки", "Супы", "Десерты".
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeCategoryCreateRequest {

    /**
     * Название категории рецептов.
     * Обязательное поле. Длина от 2 до 50 символов.
     */
    @NotBlank(message = "name must be not empty")
    @Size(min = 2, max = 50, message = "name must be between 2 and 50 characters long")
    private String name;

    /**
     * Краткое описание категории.
     * Необязательное поле. Максимальная длина — 255 символов.
     */
    @Size(max = 255, message = "description must be at most 255 characters")
    private String description;
}
