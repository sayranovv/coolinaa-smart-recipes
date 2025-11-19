package com.coolinaa.repository;

import com.coolinaa.entity.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {

    Page<Recipe> findByIsPublicTrueAndStatus(String status, Pageable pageable);
    List<Recipe> findByUserId(Integer userId);
    Page<Recipe> findByUserIdAndStatus(Integer userId, String status, Pageable pageable);

    @Query("SELECT r FROM Recipe r WHERE r.isPublic = true AND r.status = 'active' " +
            "AND (LOWER(r.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(r.description) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    Page<Recipe> searchByTitleOrDescription(@Param("searchTerm") String searchTerm, Pageable pageable);

    Page<Recipe> findByPublicTrueAndStatusAndCategory(String status, Integer categoryId, Pageable pageable);

}
