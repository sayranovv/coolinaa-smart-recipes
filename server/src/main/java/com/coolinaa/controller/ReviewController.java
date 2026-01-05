package com.coolinaa.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coolinaa.dto.request.ReviewCreateRequest;
import com.coolinaa.dto.response.ReviewResponse;
import com.coolinaa.entity.User;
import com.coolinaa.exception.UnauthorizedException;
import com.coolinaa.service.ReviewService;
import com.coolinaa.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/recipes/{recipeId}/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<ReviewResponse>> listByRecipe(@PathVariable Integer recipeId) {
        return ResponseEntity.ok(reviewService.listByRecipe(recipeId));
    }

    @PostMapping
    public ResponseEntity<ReviewResponse> create(@PathVariable Integer recipeId,
                                                 @Valid @RequestBody ReviewCreateRequest request) {
        User user = currentUser();
        return ResponseEntity.ok(reviewService.create(recipeId, user, request));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> delete(@PathVariable Integer recipeId,
                                       @PathVariable Integer reviewId) {
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
