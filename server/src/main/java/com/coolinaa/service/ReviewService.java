package com.coolinaa.service;

import com.coolinaa.dto.request.ReviewCreateRequest;
import com.coolinaa.dto.response.ReviewResponse;
import com.coolinaa.entity.Recipe;
import com.coolinaa.entity.Review;
import com.coolinaa.entity.User;
import com.coolinaa.exception.BadRequestException;
import com.coolinaa.exception.NotFoundException;
import com.coolinaa.mapper.ReviewMapper;
import com.coolinaa.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final RecipeService recipeService;

    public Page<ReviewResponse> listByRecipe(Integer recipeId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return reviewRepository.findByRecipe_Id(recipeId, pageable)
                .map(ReviewMapper::toResponse);
    }

    @Transactional
    public ReviewResponse create(Integer recipeId, User user, ReviewCreateRequest request) {
        if (reviewRepository.findByRecipe_IdAndUser_Id(recipeId, user.getId()).isPresent()) {
            throw new BadRequestException("review already exists for user");
        }
        Recipe recipe = recipeService.getEntity(recipeId);
        Review review = Review.builder()
                .recipe(recipe)
                .user(user)
                .rating(request.getRating())
                .comment(request.getComment())
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .build();
        return ReviewMapper.toResponse(reviewRepository.save(review));
    }

    @Transactional
    public ReviewResponse update(Integer reviewId, User user, ReviewCreateRequest request) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundException("review not found"));
        if (!review.getUser().getId().equals(user.getId())) {
            throw new NotFoundException("review not found for user");
        }
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        review.setUpdatedAt(OffsetDateTime.now());
        return ReviewMapper.toResponse(reviewRepository.save(review));
    }

    @Transactional
    public void delete(Integer reviewId, User user) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundException("review not found"));
        if (!review.getUser().getId().equals(user.getId())) {
            throw new NotFoundException("review not found for user");
        }
        reviewRepository.delete(review);
    }

    public ReviewResponse getById(Integer reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundException("review not found"));
        return ReviewMapper.toResponse(review);
    }
}
