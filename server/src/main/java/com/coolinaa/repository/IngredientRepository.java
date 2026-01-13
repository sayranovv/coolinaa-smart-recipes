package com.coolinaa.repository;

import com.coolinaa.entity.Ingredient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для управления ингредиентами.
 * Поддерживает методы поиска активных ингредиентов с фильтрацией по категориям и названию,
 * а также проверку уникальности имени.
 */
@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {

    Page<Ingredient> findByIsActiveTrueAndCategoryId(Integer categoryId, Pageable pageable);

    Page<Ingredient> findByIsActiveTrue(Pageable pageable);

    Page<Ingredient> findByIsActiveTrueAndNameContainingIgnoreCase(String name, Pageable pageable);

    Boolean existsByName(String name);

}
