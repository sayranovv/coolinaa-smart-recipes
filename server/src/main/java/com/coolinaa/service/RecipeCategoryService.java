package com.coolinaa.service;

import com.coolinaa.dto.request.RecipeCategoryCreateRequest;
import com.coolinaa.dto.response.RecipeCategoryResponse;
import com.coolinaa.entity.RecipeCategory;
import com.coolinaa.exception.NotFoundException;
import com.coolinaa.mapper.RecipeCategoryMapper;
import com.coolinaa.repository.RecipeCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeCategoryService {

    private final RecipeCategoryRepository repository;

    public List<RecipeCategoryResponse> findAll() {
        return repository.findAll().stream()
                .map(RecipeCategoryMapper::toResponse)
                .collect(Collectors.toList());
    }

    public RecipeCategoryResponse create(RecipeCategoryCreateRequest request) {
        RecipeCategory category = RecipeCategory.builder()
                .name(request.getName())
                .description(request.getDescription())
                .createdAt(OffsetDateTime.now())
                .build();
        category = repository.save(category);
        return RecipeCategoryMapper.toResponse(category);
    }

    public RecipeCategoryResponse update(Integer id, RecipeCategoryCreateRequest request) {
        RecipeCategory category = findEntity(id);
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setUpdatedAt(OffsetDateTime.now());
        category = repository.save(category);
        return RecipeCategoryMapper.toResponse(category);
    }

    public void delete(Integer id) {
        RecipeCategory category = findEntity(id);
        repository.delete(category);
    }

    public RecipeCategory findEntity(Integer id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("recipe category not found"));
    }
}
