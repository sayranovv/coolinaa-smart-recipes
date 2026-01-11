package com.coolinaa.controller;

import com.coolinaa.dto.response.IngredientResponse;
import com.coolinaa.entity.IngredientCategory;
import com.coolinaa.service.IngredientCategoryService;
import com.coolinaa.service.IngredientService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * REST-контроллер для управления ингредиентами.
 * Поддерживает пагинацию, фильтрацию по категориям и поиск.
 */
@RestController
@RequestMapping("/api/v1/ingredients")
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;
    private final IngredientCategoryService categoryService;

    /**
     * Получает страницу ингредиентов с возможностью фильтрации.
     *
     * @param page       номер страницы (начиная с 0), по умолчанию 0
     * @param size       количество элементов на странице, по умолчанию 100
     * @param categoryId ID категории для фильтрации (опционально)
     * @param search     строка для поиска по названию ингредиента (опционально)
     * @return {@link Page} с объектами {@link IngredientResponse}
     */
    @GetMapping
    public ResponseEntity<Page<IngredientResponse>> list(@RequestParam(defaultValue = "0") @Min(0) int page,
                                                         @RequestParam(defaultValue = "100") @Min(1) int size,
                                                         @RequestParam(required = false) Integer categoryId,
                                                         @RequestParam(required = false) String search) {
        return ResponseEntity.ok(ingredientService.getPage(categoryId, search, page, size));
    }

    /**
     * Создает новый ингредиент.
     * <p>
     * Ожидает JSON с полями: name, description, categoryId (опционально).
     * </p>
     *
     * @param body карта с данными ингредиента
     * @return созданный {@link IngredientResponse}
     */
    @PostMapping
    public ResponseEntity<IngredientResponse> create(@RequestBody Map<String, Object> body) {
        String name = (String) body.get("name");
        String description = (String) body.get("description");
        Integer categoryId = body.get("categoryId") != null ? ((Number) body.get("categoryId")).intValue() : null;
        IngredientCategory category = categoryId != null ? categoryService.findEntity(categoryId) : null;
        return ResponseEntity.ok(ingredientService.create(name, description, category));
    }

    /**
     * Обновляет существующий ингредиент.
     * <p>
     * Ожидает JSON с полями: name, description, isActive (опционально), categoryId (опционально).
     * </p>
     *
     * @param id   ID ингредиента для обновления
     * @param body карта с обновленными данными ингредиента
     * @return обновленный {@link IngredientResponse}
     */
    @PutMapping("/{id}")
    public ResponseEntity<IngredientResponse> update(@PathVariable Integer id, @RequestBody Map<String, Object> body) {
        String name = (String) body.get("name");
        String description = (String) body.get("description");
        Boolean isActive = body.get("isActive") != null ? (Boolean) body.get("isActive") : null;
        Integer categoryId = body.get("categoryId") != null ? ((Number) body.get("categoryId")).intValue() : null;
        IngredientCategory category = categoryId != null ? categoryService.findEntity(categoryId) : null;
        return ResponseEntity.ok(ingredientService.update(id, name, description, category, isActive));
    }

    /**
     * Удаляет ингредиент по ID.
     *
     * @param id ID ингредиента для удаления
     * @return пустой ответ с кодом 204 No Content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        ingredientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
