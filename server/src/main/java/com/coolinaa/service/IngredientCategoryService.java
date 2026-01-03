package com.coolinaa.service;

import com.coolinaa.dto.response.IngredientCategoryResponse;
import com.coolinaa.entity.IngredientCategory;
import com.coolinaa.exception.NotFoundException;
import com.coolinaa.mapper.IngredientCategoryMapper;
import com.coolinaa.repository.IngredientCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IngredientCategoryService {

    private final IngredientCategoryRepository repository;

    public List<IngredientCategoryResponse> findAll() {
        return repository.findAll().stream()
                .map(IngredientCategoryMapper::toResponse)
                .collect(Collectors.toList());
    }

    public IngredientCategory findEntity(Integer id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("ingredient category not found"));
    }
}
