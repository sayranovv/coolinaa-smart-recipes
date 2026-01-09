package com.coolinaa.repository;

import com.coolinaa.entity.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Integer> {

    List<RecipeIngredient> findByRecipe_Id(Integer recipeId);

    void deleteByRecipe_Id(Integer recipeId);

}
