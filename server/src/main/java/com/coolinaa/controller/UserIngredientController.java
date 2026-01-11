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

/**
 * Контроллер для управления "холодильником" пользователя.
 * Позволяет добавлять ингредиенты, которые есть у пользователя в наличии,
 * просматривать их и удалять. Эти данные используются для подбора рецептов.
 */
@RestController
@RequestMapping("/api/v1/user-ingredients")
@RequiredArgsConstructor
public class UserIngredientController {

    private final UserIngredientService userIngredientService;
    private final UserService userService;

    /**
     * Получает постраничный список ингредиентов в "холодильнике" текущего пользователя.
     *
     * @param page Номер страницы.
     * @param size Размер страницы.
     * @return Страница {@link UserIngredientResponse}.
     */
    @GetMapping
    public ResponseEntity<Page<UserIngredientResponse>> list(@RequestParam(defaultValue = "0") @Min(0) int page,
                                                             @RequestParam(defaultValue = "20") @Min(1) int size) {
        User user = currentUser();
        return ResponseEntity.ok(userIngredientService.list(user.getId(), page, size));
    }

    /**
     * Получает полный список всех ингредиентов пользователя без пагинации.
     *
     * @return Список всех ингредиентов пользователя.
     */
    @GetMapping("/all")
    public ResponseEntity<List<UserIngredientResponse>> listAll() {
        User user = currentUser();
        return ResponseEntity.ok(userIngredientService.listAll(user.getId()));
    }

    /**
     * Добавляет ингредиент в "холодильник" пользователя.
     *
     * @param request DTO {@link UserIngredientRequest} с ID ингредиента и его количеством/мерой.
     * @return Добавленная запись.
     */
    @PostMapping
    public ResponseEntity<UserIngredientResponse> add(@Valid @RequestBody UserIngredientRequest request) {
        User user = currentUser();
        return ResponseEntity.ok(userIngredientService.add(user, request));
    }

    /**
     * Удаляет ингредиент из списка пользователя.
     *
     * @param ingredientId ID ингредиента для удаления.
     * @return 204 No Content.
     */
    @DeleteMapping("/{ingredientId}")
    public ResponseEntity<Void> delete(@PathVariable Integer ingredientId) {
        User user = currentUser();
        userIngredientService.delete(user.getId(), ingredientId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Получает текущего авторизованного пользователя.
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
