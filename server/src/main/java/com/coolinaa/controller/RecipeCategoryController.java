package com.coolinaa.controller;

import com.coolinaa.dto.request.RecipeCategoryCreateRequest;
import com.coolinaa.dto.response.RecipeCategoryResponse;
import com.coolinaa.service.RecipeCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST-контроллер для управления категориями рецептов (например, "Завтраки", "Супы").
 * Обеспечивает CRUD-операции над справочником категорий.
 */
@RestController
@RequestMapping("/api/v1/recipe-categories")
@RequiredArgsConstructor
public class RecipeCategoryController {

    private final RecipeCategoryService service;

    /**
     * Возвращает список всех доступных категорий рецептов.
     *
     * @return Список DTO {@link RecipeCategoryResponse} с информацией о категориях.
     */
    @GetMapping
    public ResponseEntity<List<RecipeCategoryResponse>> list() {
        return ResponseEntity.ok(service.findAll());
    }

    /**
     * Добавляет новую категорию рецептов в справочник.
     *
     * @param request DTO {@link RecipeCategoryCreateRequest} с именем и описанием категории.
     * @return Созданная категория с присвоенным идентификатором.
     */
    @PostMapping
    public ResponseEntity<RecipeCategoryResponse> create(@Valid @RequestBody RecipeCategoryCreateRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    /**
     * Изменяет данные существующей категории рецептов.
     *
     * @param id Уникальный идентификатор обновляемой категории.
     * @param request DTO с новыми данными.
     * @return Обновленная информация о категории.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RecipeCategoryResponse> update(@PathVariable Integer id,
                                                         @Valid @RequestBody RecipeCategoryCreateRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    /**
     * Удаляет категорию рецептов из системы.
     *
     * @param id Идентификатор удаляемой категории.
     * @return Статус 204 No Content при успешном удалении.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
