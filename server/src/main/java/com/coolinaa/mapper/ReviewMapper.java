package com.coolinaa.mapper;

import com.coolinaa.dto.response.ReviewResponse;
import com.coolinaa.entity.Review;

public final class ReviewMapper {
    private ReviewMapper() {}

    public static ReviewResponse toResponse(Review review) {
        if (review == null) {
            return null;
        }
        return ReviewResponse.builder()
                .id(review.getId())
                .rating(review.getRating())
                .comment(review.getComment())
                .author(UserMapper.toResponse(review.getUser()))
                .recipeId(review.getRecipe() != null ? review.getRecipe().getId() : null)
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .build();
    }
}
