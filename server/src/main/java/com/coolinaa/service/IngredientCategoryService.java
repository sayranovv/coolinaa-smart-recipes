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

/**
 * Сервис управления категориями ингредиентов (CRUD).
 */
@Service
@RequiredArgsConstructor
public class IngredientCategoryService {

    private final IngredientCategoryRepository repository;

    /**
     * Возвращает список всех категорий.
     */
    public List<IngredientCategoryResponse> findAll() {
        return repository.findAll().stream()
                .map(IngredientCategoryMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Создает новую категорию ингредиентов.
     */
    public IngredientCategoryResponse create(IngredientCategoryRequest request) {
        IngredientCategory category = IngredientCategory.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
        return IngredientCategoryMapper.toResponse(repository.save(category));
    }

    /**
     * Обновляет существующую категорию.
     * @throws NotFoundException если категория не найдена
     */
    public IngredientCategoryResponse update(Integer id, IngredientCategoryRequest request) {
        IngredientCategory category = findEntity(id);
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        return IngredientCategoryMapper.toResponse(repository.save(category));
    }

    /**
     * Удаляет категорию по ID.
     */
    public void delete(Integer id) {
        IngredientCategory category = findEntity(id);
        repository.delete(category);
    }

    /**
     * Внутренний метод поиска сущности для использования в других сервисах.
     */
    public IngredientCategory findEntity(Integer id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("ingredient category not found"));
    }
}
