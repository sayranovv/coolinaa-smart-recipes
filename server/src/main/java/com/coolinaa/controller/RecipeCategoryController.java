package com.coolinaa.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.coolinaa.dto.request.RecipeCategoryCreateRequest;
import com.coolinaa.dto.response.RecipeCategoryResponse;
import com.coolinaa.service.RecipeCategoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/recipe-categories")
@RequiredArgsConstructor
public class RecipeCategoryController {

    private final RecipeCategoryService service;

    @GetMapping
    public ResponseEntity<List<RecipeCategoryResponse>> list() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    public ResponseEntity<RecipeCategoryResponse> create(@Valid @RequestBody RecipeCategoryCreateRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeCategoryResponse> update(@PathVariable Integer id,
                                                         @Valid @RequestBody RecipeCategoryCreateRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
