package com.coolinaa.controller;

import com.coolinaa.dto.request.RecipeCreateRequest;
import com.coolinaa.dto.response.RecipeMatchResponse;
import com.coolinaa.dto.response.RecipeResponse;
import com.coolinaa.entity.User;
import com.coolinaa.enums.RecipeStatus;
import com.coolinaa.exception.UnauthorizedException;
import com.coolinaa.service.RecipeService;
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
@RequestMapping("/api/v1/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<Page<RecipeResponse>> listPublic(@RequestParam(defaultValue = "0") @Min(0) int page,
                                                           @RequestParam(defaultValue = "20") @Min(1) int size,
                                                           @RequestParam(required = false) String search,
                                                           @RequestParam(required = false) Integer categoryId) {
        return ResponseEntity.ok(recipeService.listPublic(page, size, search, categoryId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeResponse> get(@PathVariable Integer id) {
        return ResponseEntity.ok(recipeService.getById(id));
    }

    @PostMapping
    public ResponseEntity<RecipeResponse> create(@Valid @RequestBody RecipeCreateRequest request) {
        User user = currentUser();
        return ResponseEntity.ok(recipeService.create(request, user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeResponse> update(@PathVariable Integer id,
                                                 @Valid @RequestBody RecipeCreateRequest request) {
        User user = currentUser();
        return ResponseEntity.ok(recipeService.update(id, request, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        User user = currentUser();
        recipeService.delete(id, user);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<RecipeResponse> changeStatus(@PathVariable Integer id,
                                                       @RequestParam RecipeStatus status) {
        User user = currentUser();
        return ResponseEntity.ok(recipeService.changeStatus(id, status, user));
    }

    @GetMapping("/match/me")
    public ResponseEntity<java.util.List<RecipeMatchResponse>> matchForCurrentUser() {
        User user = currentUser();
        return ResponseEntity.ok(recipeService.matchByUser(user.getId()));
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
