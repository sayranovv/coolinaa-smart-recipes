package com.coolinaa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coolinaa.entity.Ingredient;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {

    Page<Ingredient> findByIsActiveTrueAndCategoryId(Integer categoryId, Pageable pageable);
    Page<Ingredient> findByIsActiveTrue(Pageable pageable);
    Page<Ingredient> findByIsActiveTrueAndNameContainingIgnoreCase(String name, Pageable pageable);
    Boolean existsByName(String name);

}
