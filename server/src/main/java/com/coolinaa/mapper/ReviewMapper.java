package com.coolinaa.mapper;

import com.coolinaa.dto.response.ReviewResponse;
import com.coolinaa.entity.Review;

public final class ReviewMapper {
    private ReviewMapper() {}

    public static ReviewResponse toResponse(Review review) {
        if (review == null) {
            return null;
        }
        ReviewResponse.User user = null;
        if (review.getUser() != null) {
            user = ReviewResponse.User.builder()
                    .id(review.getUser().getId())
                    .username(review.getUser().getUsername())
                    .build();
        }
        return ReviewResponse.builder()
                .id(review.getId())
                .rating(review.getRating())
                .comment(review.getComment())
                .userId(review.getUser() != null ? review.getUser().getId() : null)
                .user(user)
                .recipeId(review.getRecipe() != null ? review.getRecipe().getId() : null)
                .createdAt(review.getCreatedAt())
                .build();
    }
}
