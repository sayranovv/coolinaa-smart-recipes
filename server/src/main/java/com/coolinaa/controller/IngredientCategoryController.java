package com.coolinaa.controller;

import com.coolinaa.dto.request.IngredientCategoryRequest;
import com.coolinaa.dto.response.IngredientCategoryResponse;
import com.coolinaa.service.IngredientCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * REST-контроллер для управления категориями ингредиентов.
 * Позволяет просматривать список категорий, а также создавать, обновлять и удалять их.
 * В отличие от самих ингредиентов, список категорий обычно невелик, поэтому пагинация не требуется.
 */
@RestController
@RequestMapping("/api/v1/ingredient-categories")
@RequiredArgsConstructor
public class IngredientCategoryController {

    private final IngredientCategoryService service;

    /**
     * Получает полный список всех категорий ингредиентов.
     *
     * @return Список объектов {@link IngredientCategoryResponse}, представляющих все доступные категории.
     */
    @GetMapping
    public ResponseEntity<List<IngredientCategoryResponse>> list() {
        return ResponseEntity.ok(service.findAll());
    }

    /**
     * Создает новую категорию ингредиентов.
     *
     * @param request Объект {@link IngredientCategoryRequest}, содержащий название и описание новой категории.
     * @return Объект {@link IngredientCategoryResponse} с данными созданной категории, включая присвоенный ID.
     */
    @PostMapping
    public ResponseEntity<IngredientCategoryResponse> create(@Valid @RequestBody IngredientCategoryRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    /**
     * Обновляет существующую категорию ингредиентов.
     *
     * @param id ID категории, которую необходимо обновить.
     * @param request Объект {@link IngredientCategoryRequest} с новыми данными для категории.
     * @return Обновленный объект {@link IngredientCategoryResponse}.
     */
    @PutMapping("/{id}")
    public ResponseEntity<IngredientCategoryResponse> update(@PathVariable Integer id,
                                                             @Valid @RequestBody IngredientCategoryRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    /**
     * Удаляет категорию ингредиентов по её ID.
     *
     * @param id ID категории для удаления.
     * @return Пустой ответ со статусом 204 No Content в случае успешного удаления.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
