package com.coolinaa.service;

import com.coolinaa.dto.response.IngredientResponse;
import com.coolinaa.entity.Ingredient;
import com.coolinaa.entity.IngredientCategory;
import com.coolinaa.exception.ConflictException;
import com.coolinaa.exception.NotFoundException;
import com.coolinaa.mapper.IngredientMapper;
import com.coolinaa.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

/**
 * Сервис для управления ингредиентами.
 * Обеспечивает поиск, фильтрацию и управление жизненным циклом ингредиентов.
 */
@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    /**
     * Получает страницу ингредиентов с фильтрацией по категории.
     */
    public Page<IngredientResponse> getPage(Integer categoryId, int page, int size) {
        return getPage(categoryId, null, page, size);
    }

    /**
     * Получает страницу ингредиентов с поиском по имени и фильтрацией по категории.
     * Поиск по имени имеет приоритет над фильтром по категории.
     */
    public Page<IngredientResponse> getPage(Integer categoryId, String search, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Ingredient> result;
        if (search != null && !search.isBlank()) {
            result = ingredientRepository.findByIsActiveTrueAndNameContainingIgnoreCase(search, pageable);
        } else if (categoryId != null) {
            result = ingredientRepository.findByIsActiveTrueAndCategoryId(categoryId, pageable);
        } else {
            result = ingredientRepository.findByIsActiveTrue(pageable);
        }
        return result.map(IngredientMapper::toResponse);
    }

    /**
     * Создает новый ингредиент.
     * @throws ConflictException если ингредиент с таким именем уже существует
     */
    public IngredientResponse create(String name, String description, IngredientCategory category) {
        if (ingredientRepository.existsByName(name)) {
            throw new ConflictException("ingredient already exists");
        }
        Ingredient ingredient = Ingredient.builder()
                .name(name)
                .description(description)
                .category(category)
                .isActive(true)
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .build();
        return IngredientMapper.toResponse(ingredientRepository.save(ingredient));
    }

    /**
     * Обновляет данные ингредиента.
     * Позволяет частично обновлять поля (null значения игнорируются).
     */
    public IngredientResponse update(Integer id, String name, String description, IngredientCategory category, Boolean isActive) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("ingredient not found"));
        if (name != null) ingredient.setName(name);
        if (description != null) ingredient.setDescription(description);
        if (category != null) ingredient.setCategory(category);
        if (isActive != null) ingredient.setIsActive(isActive);
        ingredient.setUpdatedAt(OffsetDateTime.now());
        return IngredientMapper.toResponse(ingredientRepository.save(ingredient));
    }

    /**
     * Удаляет ингредиент.
     */
    public void delete(Integer id) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("ingredient not found"));
        ingredientRepository.delete(ingredient);
    }

    /**
     * Поиск сущности ингредиента. Используется другими сервисами (например, при создании рецепта).
     */
    public Ingredient findEntity(Integer id) {
        return ingredientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("ingredient not found"));
    }
}
