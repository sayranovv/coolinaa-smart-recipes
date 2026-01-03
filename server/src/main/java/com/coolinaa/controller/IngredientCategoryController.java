package com.coolinaa.controller;

import com.coolinaa.dto.response.IngredientCategoryResponse;
import com.coolinaa.service.IngredientCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
