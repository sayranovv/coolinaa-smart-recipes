package com.coolinaa.controller;

import com.coolinaa.dto.request.ReviewCreateRequest;
import com.coolinaa.dto.response.ReviewResponse;
import com.coolinaa.entity.User;
import com.coolinaa.exception.UnauthorizedException;
import com.coolinaa.service.ReviewService;
import com.coolinaa.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userService;

    @GetMapping("/recipe/{recipeId}")
    public ResponseEntity<Page<ReviewResponse>> listByRecipe(@PathVariable Integer recipeId,
                                                             @RequestParam(defaultValue = "0") @Min(0) int page,
                                                             @RequestParam(defaultValue = "20") @Min(1) int size) {
        return ResponseEntity.ok(reviewService.listByRecipe(recipeId, page, size));
    }

    @PostMapping("/recipe/{recipeId}")
    public ResponseEntity<ReviewResponse> create(@PathVariable Integer recipeId,
                                                 @Valid @RequestBody ReviewCreateRequest request) {
        User user = currentUser();
        return ResponseEntity.ok(reviewService.create(recipeId, user, request));
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewResponse> update(@PathVariable Integer reviewId,
                                                 @Valid @RequestBody ReviewCreateRequest request) {
        User user = currentUser();
        return ResponseEntity.ok(reviewService.update(reviewId, user, request));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> delete(@PathVariable Integer reviewId) {
        User user = currentUser();
        reviewService.delete(reviewId, user);
        return ResponseEntity.noContent().build();
    }

    private User currentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new UnauthorizedException("auth required");
        }
        User user = userService.getByUsernameOrEmail(auth.getName());
        if (user == null) {
            throw new UnauthorizedException("auth required");
        }
        return user;
    }
}
