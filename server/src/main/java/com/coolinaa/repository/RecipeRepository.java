package com.coolinaa.repository;

import com.coolinaa.entity.Recipe;
import com.coolinaa.enums.RecipeStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Основной репозиторий для работы с рецептами.
 * Реализует сложную логику выборки:
 * - Публичные рецепты для ленты
 * - Поиск по названию и описанию (case-insensitive)
 * - Фильтрация по статусу и категории
 * - Получение рецептов конкретного пользователя
 */
@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {

    Page<Recipe> findByIsPublicTrueAndStatus(RecipeStatus status, Pageable pageable);

    List<Recipe> findByUser_Id(Integer userId);

    Page<Recipe> findByUser_Id(Integer userId, Pageable pageable);

    Page<Recipe> findByUser_IdAndStatus(Integer userId, RecipeStatus status, Pageable pageable);

    @Query("SELECT r FROM Recipe r WHERE r.isPublic = true AND r.status = :status " +
            "AND (LOWER(r.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(r.description) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    Page<Recipe> searchByTitleOrDescription(@Param("searchTerm") String searchTerm,
                                            @Param("status") RecipeStatus status,
                                            Pageable pageable);

    Page<Recipe> findByIsPublicTrueAndStatusAndCategoryId(RecipeStatus status, Integer categoryId, Pageable pageable);

}
