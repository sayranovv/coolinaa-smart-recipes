package com.coolinaa.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * DTO для создания отзыва к рецепту.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewCreateRequest {

    /**
     * Числовая оценка рецепта от 1 до 5.
     * Обязательное поле.
     */
    @NotNull(message = "rating is required")
    @Min(value = 1, message = "rating must be from 1")
    @Max(value = 5, message = "rating must be max 5")
    private Integer rating;

    /**
     * Текстовый комментарий пользователя.
     * Необязательное поле. Максимум 1000 символов.
     */
    @Size(max = 1000, message = "comment must not exceed 1000 characters")
    private String comment;

}
