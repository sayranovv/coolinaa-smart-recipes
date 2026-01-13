package com.coolinaa.repository;

import com.coolinaa.entity.RecipeCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для управления категориями рецептов (справочник).
 * Используется для получения списка доступных категорий (Завтраки, Супы и т.д.).
 */
@Repository
public interface RecipeCategoryRepository extends JpaRepository<RecipeCategory, Integer> {
}
