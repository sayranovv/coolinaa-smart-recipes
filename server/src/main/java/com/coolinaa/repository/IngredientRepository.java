package com.coolinaa.repository;

import com.coolinaa.entity.Ingredient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {

    Page<Ingredient> findByIsActiveTrueAndCategory(Integer categoryId, Pageable pageable);
    Page<Ingredient> findByIsActiveTrue(Pageable pageable);
    Boolean existsByName(String name);

}
