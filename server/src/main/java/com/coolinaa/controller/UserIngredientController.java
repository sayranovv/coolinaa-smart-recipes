package com.coolinaa.controller;

import com.coolinaa.dto.request.UserIngredientRequest;
import com.coolinaa.dto.response.UserIngredientResponse;
import com.coolinaa.entity.User;
import com.coolinaa.exception.UnauthorizedException;
import com.coolinaa.service.UserIngredientService;
import com.coolinaa.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user-ingredients")
@RequiredArgsConstructor
public class UserIngredientController {

    private final UserIngredientService userIngredientService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserIngredientResponse>> list(@RequestParam(defaultValue = "0") @Min(0) int page,
                                                             @RequestParam(defaultValue = "20") @Min(1) int size) {
        User user = currentUser();
        return ResponseEntity.ok(userIngredientService.list(user.getId(), page, size));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserIngredientResponse>> listAll() {
        User user = currentUser();
        return ResponseEntity.ok(userIngredientService.listAll(user.getId()));
    }

    @PostMapping
    public ResponseEntity<UserIngredientResponse> add(@Valid @RequestBody UserIngredientRequest request) {
        User user = currentUser();
        return ResponseEntity.ok(userIngredientService.add(user, request));
    }

    @DeleteMapping("/{ingredientId}")
    public ResponseEntity<Void> delete(@PathVariable Integer ingredientId) {
        User user = currentUser();
        userIngredientService.delete(user.getId(), ingredientId);
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
