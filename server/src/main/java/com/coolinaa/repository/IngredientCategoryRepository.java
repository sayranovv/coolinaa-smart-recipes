package com.coolinaa.repository;

import com.coolinaa.entity.IngredientCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с категориями ингредиентов.
 * Предоставляет стандартные методы CRUD и пагинации для сущности {@link IngredientCategory}.
 */
@Repository
public interface IngredientCategoryRepository extends JpaRepository<IngredientCategory, Integer> {
}
