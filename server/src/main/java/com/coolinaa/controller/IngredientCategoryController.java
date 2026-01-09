package com.coolinaa.controller;

import com.coolinaa.dto.request.IngredientCategoryRequest;
import com.coolinaa.dto.response.IngredientCategoryResponse;
import com.coolinaa.service.IngredientCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ingredient-categories")
@RequiredArgsConstructor
public class IngredientCategoryController {

    private final IngredientCategoryService service;

    @GetMapping
    public ResponseEntity<List<IngredientCategoryResponse>> list() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    public ResponseEntity<IngredientCategoryResponse> create(@Valid @RequestBody IngredientCategoryRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<IngredientCategoryResponse> update(@PathVariable Integer id,
                                                             @Valid @RequestBody IngredientCategoryRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
