package com.coolinaa.repository;

import com.coolinaa.entity.UserIngredient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserIngredientRepository extends JpaRepository<UserIngredient, Integer> {

    List<UserIngredient> findByUserId(Integer userId);
    Page<UserIngredient> findByUserId(Integer userId, Pageable pageable);
    Optional<UserIngredient> findByUserIdAndIngredientId(Integer userId, Integer ingredientId);
    List<UserIngredient> findByUserIdAndExpiresAtBefore(Integer userId, OffsetDateTime date);

}
