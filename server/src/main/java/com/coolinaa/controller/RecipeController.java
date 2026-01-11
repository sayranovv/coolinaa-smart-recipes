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

/**
 * Основной контроллер для работы с рецептами.
 * Поддерживает публичный просмотр рецептов, управление собственными рецептами пользователя,
 * поиск рецептов по ингредиентам ("что приготовить") и изменение статусов.
 */
@RestController
@RequestMapping("/api/v1/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;
    private final UserService userService;

    /**
     * Публичный поиск и просмотр списка рецептов.
     * Доступен без аутентификации.
     *
     * @param page Номер страницы (начиная с 0), по умолчанию 0.
     * @param size Размер страницы, по умолчанию 20.
     * @param search Строка поискового запроса (фильтрация по названию/описанию), опционально.
     * @param categoryId Фильтр по ID категории, опционально.
     * @return Страница с краткими данными рецептов {@link RecipeResponse}.
     */
    @GetMapping
    public ResponseEntity<Page<RecipeResponse>> listPublic(@RequestParam(defaultValue = "0") @Min(0) int page,
                                                           @RequestParam(defaultValue = "20") @Min(1) int size,
                                                           @RequestParam(required = false) String search,
                                                           @RequestParam(required = false) Integer categoryId) {
        return ResponseEntity.ok(recipeService.listPublic(page, size, search, categoryId));
    }

    /**
     * Получение детальной информации об одном рецепте по его ID.
     *
     * @param id Идентификатор рецепта.
     * @return Полная информация о рецепте {@link RecipeResponse}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RecipeResponse> get(@PathVariable Integer id) {
        return ResponseEntity.ok(recipeService.getById(id));
    }

    /**
     * Создание нового рецепта от имени текущего авторизованного пользователя.
     *
     * @param request Данные нового рецепта {@link RecipeCreateRequest} (название, ингредиенты, шаги).
     * @return Созданный рецепт.
     * @throws UnauthorizedException если пользователь не авторизован.
     */
    @PostMapping
    public ResponseEntity<RecipeResponse> create(@Valid @RequestBody RecipeCreateRequest request) {
        User user = currentUser();
        return ResponseEntity.ok(recipeService.create(request, user));
    }

    /**
     * Обновление существующего рецепта.
     * Разрешено только автору рецепта.
     *
     * @param id ID редактируемого рецепта.
     * @param request Новые данные рецепта.
     * @return Обновленный рецепт.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RecipeResponse> update(@PathVariable Integer id,
                                                 @Valid @RequestBody RecipeCreateRequest request) {
        User user = currentUser();
        return ResponseEntity.ok(recipeService.update(id, request, user));
    }

    /**
     * Удаление рецепта.
     * Разрешено только автору или администратору.
     *
     * @param id ID удаляемого рецепта.
     * @return Статус 204 No Content.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        User user = currentUser();
        recipeService.delete(id, user);
        return ResponseEntity.noContent().build();
    }

    /**
     * Изменение статуса публикации рецепта (например, DRAFT -> PUBLISHED).
     *
     * @param id ID рецепта.
     * @param status Новый статус {@link RecipeStatus}.
     * @return Рецепт с обновленным статусом.
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<RecipeResponse> changeStatus(@PathVariable Integer id,
                                                       @RequestParam RecipeStatus status) {
        User user = currentUser();
        return ResponseEntity.ok(recipeService.changeStatus(id, status, user));
    }

    /**
     * Подбор рецептов на основе ингредиентов, имеющихся у текущего пользователя ("Холодильник").
     * Анализирует список UserIngredient текущего пользователя и ищет подходящие рецепты.
     *
     * @return Список рецептов с информацией о совпадающих ингредиентах {@link RecipeMatchResponse}.
     */
    @GetMapping("/match/me")
    public ResponseEntity<java.util.List<RecipeMatchResponse>> matchForCurrentUser() {
        User user = currentUser();
        return ResponseEntity.ok(recipeService.matchByUser(user.getId()));
    }

    /**
     * Получение списка рецептов, созданных текущим пользователем ("Мои рецепты").
     *
     * @param page Номер страницы.
     * @param size Размер страницы.
     * @return Страница с рецептами пользователя.
     */
    @GetMapping("/my")
    public ResponseEntity<Page<RecipeResponse>> myRecipes(@RequestParam(defaultValue = "0") @Min(0) int page,
                                                          @RequestParam(defaultValue = "20") @Min(1) int size) {
        User user = currentUser();
        return ResponseEntity.ok(recipeService.listByUser(user.getId(), page, size));
    }

    /**
     * Вспомогательный метод для извлечения текущего аутентифицированного пользователя из SecurityContext.
     *
     * @return Объект {@link User}, соответствующий текущему токену.
     * @throws UnauthorizedException если контекст пуст или пользователь не найден в БД.
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
