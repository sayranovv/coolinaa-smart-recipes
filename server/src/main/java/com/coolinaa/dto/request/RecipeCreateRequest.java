package com.coolinaa.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * DTO для создания или полного обновления рецепта.
 * Содержит всю информацию о рецепте, включая список ингредиентов.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeCreateRequest {

    /**
     * Заголовок рецепта.
     * Обязательное поле. Максимум 200 символов.
     */
    @NotBlank(message = "title must be not empty")
    @Size(max = 200, message = "title must not exceed 200 characters")
    private String title;

    /**
     * Краткое описание или вступление к рецепту.
     * Необязательное поле. Максимум 1000 символов.
     */
    @Size(max = 1000, message = "description must not exceed 1000 characters")
    private String description;

    /**
     * Полный текст инструкции по приготовлению.
     * Может содержать разметку или просто текст. Обязательное поле.
     */
    @NotBlank(message = "instructions must be not empty")
    private String instructions;

    /**
     * Время на подготовку ингредиентов (нарезка, чистка) в минутах.
     * Должно быть положительным числом.
     */
    @Positive(message = "preparation time must me positive number")
    private Integer preparationTime;

    /**
     * Время тепловой обработки или непосредственного приготовления в минутах.
     * Должно быть положительным числом.
     */
    @Positive(message = "cooking time must be positive number")
    private Integer cookingTime;

    /**
     * Уровень сложности рецепта по шкале от 1 (очень легко) до 5 (профессионально).
     */
    @Min(value = 1, message = "difficulty level must be from 1")
    @Max(value = 5, message = "difficulty level must be max 5")
    private Integer difficultyLevel;

    /**
     * Количество порций, на которое рассчитан рецепт.
     * Положительное число.
     */
    @Positive(message = "servings must be positive number")
    private Integer servings;

    /**
     * Ссылка на изображение готового блюда.
     * Максимум 255 символов.
     */
    @Size(max = 255, message = "image url must not exceed 255 characters")
    private String imageUrl;

    /**
     * ID категории, к которой относится рецепт (например, "Супы").
     * Может быть null, если категория не выбрана.
     */
    private Integer categoryId;

    /**
     * Список ингредиентов, необходимых для рецепта.
     * Не может быть пустым.
     */
    @NotEmpty(message = "ingredients must be not empty")
    private List<RecipeIngredientRequest> ingredients;

    /**
     * Флаг публичной доступности рецепта.
     * true - рецепт виден всем пользователям.
     * false - рецепт виден только автору (черновик).
     * По умолчанию true.
     */
    private Boolean isPublic = true;
}
