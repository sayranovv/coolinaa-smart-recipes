package com.coolinaa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coolinaa.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    List<Review> findByRecipe_Id(Integer recipeId);
    Page<Review> findByRecipe_Id(Integer recipeId, Pageable pageable);
    Optional<Review> findByRecipe_IdAndUser_Id(Integer recipeId, Integer userId);
    List<Review> findByUser_Id(Integer userId);

}
