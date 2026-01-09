package com.coolinaa.service;

import com.coolinaa.dto.request.IngredientCategoryRequest;
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

    public IngredientCategoryResponse create(IngredientCategoryRequest request) {
        IngredientCategory category = IngredientCategory.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
        return IngredientCategoryMapper.toResponse(repository.save(category));
    }

    public IngredientCategoryResponse update(Integer id, IngredientCategoryRequest request) {
        IngredientCategory category = findEntity(id);
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        return IngredientCategoryMapper.toResponse(repository.save(category));
    }

    public void delete(Integer id) {
        IngredientCategory category = findEntity(id);
        repository.delete(category);
    }

    public IngredientCategory findEntity(Integer id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("ingredient category not found"));
    }
}
