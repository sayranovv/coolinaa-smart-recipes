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

@RestController
@RequestMapping("/api/v1/ingredients")
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;
    private final IngredientCategoryService categoryService;

    @GetMapping
    public ResponseEntity<Page<IngredientResponse>> list(@RequestParam(defaultValue = "0") @Min(0) int page,
                                                         @RequestParam(defaultValue = "20") @Min(1) int size,
                                                         @RequestParam(required = false) Integer categoryId) {
        return ResponseEntity.ok(ingredientService.getPage(categoryId, page, size));
    }

    @PostMapping
    public ResponseEntity<IngredientResponse> create(@RequestBody Map<String, Object> body) {
        String name = (String) body.get("name");
        String description = (String) body.get("description");
        Integer categoryId = body.get("categoryId") != null ? ((Number) body.get("categoryId")).intValue() : null;
        IngredientCategory category = categoryId != null ? categoryService.findEntity(categoryId) : null;
        return ResponseEntity.ok(ingredientService.create(name, description, category));
    }

    @PutMapping("/{id}")
    public ResponseEntity<IngredientResponse> update(@PathVariable Integer id, @RequestBody Map<String, Object> body) {
        String name = (String) body.get("name");
        String description = (String) body.get("description");
        Boolean isActive = body.get("isActive") != null ? (Boolean) body.get("isActive") : null;
        Integer categoryId = body.get("categoryId") != null ? ((Number) body.get("categoryId")).intValue() : null;
        IngredientCategory category = categoryId != null ? categoryService.findEntity(categoryId) : null;
        return ResponseEntity.ok(ingredientService.update(id, name, description, category, isActive));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        ingredientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
