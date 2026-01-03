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

    List<UserIngredient> findByUser_Id(Integer userId);
    Page<UserIngredient> findByUser_Id(Integer userId, Pageable pageable);
    Optional<UserIngredient> findByUser_IdAndIngredient_Id(Integer userId, Integer ingredientId);
    List<UserIngredient> findByUser_IdAndExpiresAtBefore(Integer userId, OffsetDateTime date);

}
