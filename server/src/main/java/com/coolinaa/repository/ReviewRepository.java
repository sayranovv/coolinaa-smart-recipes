package com.coolinaa.repository;

import com.coolinaa.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    Page<Review> findByRecipeId(Integer recipeId, Pageable pageable);
    Optional<Review> findByRecipeIdAndUserId(Integer recipeId, Integer userId);
    List<Review> findByUserId(Integer userId);

}
