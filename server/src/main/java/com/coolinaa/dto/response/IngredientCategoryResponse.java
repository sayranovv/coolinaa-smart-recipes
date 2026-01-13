package com.coolinaa.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

/**
 * DTO с информацией о категории ингредиентов.
 * Используется в списках и справочниках.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IngredientCategoryResponse {

    /**
     * Уникальный идентификатор категории.
     */
    private Integer id;

    /**
     * Название категории (например, "Молочные продукты").
     */
    private String name;

    /**
     * Описание категории.
     */
    private String description;

    /**
     * Дата и время создания записи.
     */
    private OffsetDateTime createdAt;

}
