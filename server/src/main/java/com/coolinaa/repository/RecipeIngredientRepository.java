package com.coolinaa.repository;

import com.coolinaa.entity.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для управления связями между рецептами и ингредиентами.
 * Позволяет получать список ингредиентов конкретного рецепта или удалять их при обновлении рецепта.
 */
@Repository
public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Integer> {

    List<RecipeIngredient> findByRecipe_Id(Integer recipeId);

    void deleteByRecipe_Id(Integer recipeId);

}
