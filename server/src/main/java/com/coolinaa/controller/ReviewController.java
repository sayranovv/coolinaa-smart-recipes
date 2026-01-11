package com.coolinaa.controller;

import com.coolinaa.dto.request.ReviewCreateRequest;
import com.coolinaa.dto.response.ReviewResponse;
import com.coolinaa.entity.User;
import com.coolinaa.exception.UnauthorizedException;
import com.coolinaa.service.ReviewService;
import com.coolinaa.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST-контроллер для работы с отзывами и комментариями к рецептам.
 * Позволяет пользователям оставлять мнение о рецептах.
 */
@RestController
@RequestMapping("/api/v1/recipes/{recipeId}/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userService;

    /**
     * Получает список всех отзывов для конкретного рецепта.
     *
     * @param recipeId ID рецепта, для которого запрашиваются отзывы.
     * @return Список объектов {@link ReviewResponse} с текстом отзыва, оценкой и автором.
     */
    @GetMapping
    public ResponseEntity<List<ReviewResponse>> listByRecipe(@PathVariable Integer recipeId) {
        return ResponseEntity.ok(reviewService.listByRecipe(recipeId));
    }

    /**
     * Добавляет новый отзыв к рецепту от имени текущего пользователя.
     *
     * @param recipeId ID комментируемого рецепта.
     * @param request DTO {@link ReviewCreateRequest} с текстом отзыва и оценкой.
     * @return Созданный отзыв.
     */
    @PostMapping
    public ResponseEntity<ReviewResponse> create(@PathVariable Integer recipeId,
                                                 @Valid @RequestBody ReviewCreateRequest request) {
        User user = currentUser();
        return ResponseEntity.ok(reviewService.create(recipeId, user, request));
    }

    /**
     * Удаляет отзыв.
     * Разрешено автору отзыва.
     *
     * @param recipeId ID рецепта (используется для проверки консистентности пути).
     * @param reviewId ID удаляемого отзыва.
     * @return Статус 204 No Content.
     */
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> delete(@PathVariable Integer recipeId,
                                       @PathVariable Integer reviewId) {
        User user = currentUser();
        reviewService.delete(reviewId, user);
        return ResponseEntity.noContent().build();
    }

    /**
     * Извлекает текущего пользователя из контекста безопасности.
     * @throws UnauthorizedException если пользователь не авторизован.
     */
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
