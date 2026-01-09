package com.coolinaa.service;

import com.coolinaa.dto.request.RecipeCreateRequest;
import com.coolinaa.dto.request.RecipeIngredientRequest;
import com.coolinaa.dto.response.RecipeResponse;
import com.coolinaa.entity.*;
import com.coolinaa.enums.RecipeStatus;
import com.coolinaa.exception.NotFoundException;
import com.coolinaa.mapper.RecipeMapper;
import com.coolinaa.repository.RecipeCategoryRepository;
import com.coolinaa.repository.RecipeIngredientRepository;
import com.coolinaa.repository.RecipeRepository;
import com.coolinaa.repository.UserIngredientRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final IngredientService ingredientService;
    private final RecipeCategoryRepository categoryRepository;
    private final UnitService unitService;
    private final UserIngredientRepository userIngredientRepository;

    @Transactional(readOnly = true)
    public Page<RecipeResponse> listPublic(int page, int size, String search, Integer categoryId) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Recipe> result;
        if (search != null && !search.isBlank()) {
            result = recipeRepository.searchByTitleOrDescription(search, RecipeStatus.ACTIVE, pageable);
        } else if (categoryId != null) {
            result = recipeRepository.findByIsPublicTrueAndStatusAndCategoryId(RecipeStatus.ACTIVE, categoryId, pageable);
        } else {
            result = recipeRepository.findByIsPublicTrueAndStatus(RecipeStatus.ACTIVE, pageable);
        }
        List<RecipeResponse> responses = result.getContent().stream()
                .map(r -> {
                    Hibernate.initialize(r.getIngredients());
                    Hibernate.initialize(r.getReviews());
                    return RecipeMapper.toResponse(r);
                })
                .collect(Collectors.toList());
        return new org.springframework.data.domain.PageImpl<>(responses, pageable, result.getTotalElements());
    }

    public Page<RecipeResponse> listUserRecipes(Integer userId, int page, int size, RecipeStatus status) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Recipe> result = recipeRepository.findByUser_IdAndStatus(userId, status, pageable);
        return result.map(RecipeMapper::toResponse);
    }

    public Page<RecipeResponse> listByUser(Integer userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Recipe> result = recipeRepository.findByUser_Id(userId, pageable);
        return result.map(RecipeMapper::toResponse);
    }

    public RecipeResponse getById(Integer id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("recipe not found"));
        return RecipeMapper.toResponse(recipe);
    }

    public Recipe getEntity(Integer id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("recipe not found"));
    }

    @Transactional(readOnly = true)
    public List<Recipe> loadAllPublic() {
        return recipeRepository.findByIsPublicTrueAndStatus(RecipeStatus.ACTIVE, PageRequest.of(0, Integer.MAX_VALUE)).getContent();
    }

    public List<com.coolinaa.dto.response.RecipeMatchResponse> matchByUser(Integer userId) {
        List<UserIngredient> fridge = userIngredientRepository.findByUser_Id(userId);
        Set<Integer> ownedIds = fridge.stream().map(ui -> ui.getIngredient().getId()).collect(Collectors.toSet());

        List<Recipe> candidates = recipeRepository.findByIsPublicTrueAndStatus(RecipeStatus.ACTIVE, PageRequest.of(0, 200)).getContent();
        return candidates.stream()
                .map(r -> buildMatch(r, ownedIds))
                .sorted((a, b) -> Double.compare(b.getMatchPercentage(), a.getMatchPercentage()))
                .collect(Collectors.toList());
    }

    private com.coolinaa.dto.response.RecipeMatchResponse buildMatch(Recipe recipe, Set<Integer> ownedIds) {
        int total = recipe.getIngredients().size();
        int matched = (int) recipe.getIngredients().stream()
                .filter(ri -> ownedIds.contains(ri.getIngredient().getId()))
                .count();
        double matchPct = total == 0 ? 0.0 : (matched * 100.0 / total);

        List<com.coolinaa.dto.response.MissingIngredientResponse> missing = recipe.getIngredients().stream()
                .filter(ri -> !ownedIds.contains(ri.getIngredient().getId()))
                .map(ri -> com.coolinaa.dto.response.MissingIngredientResponse.builder()
                        .ingredientId(ri.getIngredient().getId())
                        .ingredientName(ri.getIngredient().getName())
                        .quantity(ri.getQuantity())
                        .unitName(ri.getUnit() != null ? ri.getUnit().getName() : null)
                        .build())
                .collect(Collectors.toList());

        double avgRating = recipe.getReviews().stream()
                .mapToInt(r -> r.getRating() == null ? 0 : r.getRating())
                .average().orElse(0.0);

        return com.coolinaa.dto.response.RecipeMatchResponse.builder()
                .recipeId(recipe.getId())
                .title(recipe.getTitle())
                .description(recipe.getDescription())
                .imageUrl(recipe.getImageUrl())
                .matchPercentage(matchPct)
                .matchedIngredients(matched)
                .totalIngredients(total)
                .missingIngredients(missing)
                .cookingTime(recipe.getCookingTime())
                .difficultyLevel(recipe.getDifficultyLevel())
                .averageRating(avgRating)
                .build();
    }

    @Transactional
    public RecipeResponse create(RecipeCreateRequest request, User author) {
        Recipe recipe = new Recipe();
        applyRecipeFields(recipe, request, author);
        recipe = recipeRepository.save(recipe);

        Set<RecipeIngredient> ingredients = buildIngredients(request.getIngredients(), recipe);
        recipe.setIngredients(ingredients);
        recipe = recipeRepository.save(recipe);
        return RecipeMapper.toResponse(recipe);
    }

    @Transactional
    public RecipeResponse update(Integer recipeId, RecipeCreateRequest request, User author) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new NotFoundException("recipe not found"));
        if (!recipe.getUser().getId().equals(author.getId())) {
            throw new NotFoundException("recipe not found for user");
        }
        recipe.getIngredients().clear();
        recipeIngredientRepository.deleteByRecipe_Id(recipeId);

        applyRecipeFields(recipe, request, author);
        Set<RecipeIngredient> ingredients = buildIngredients(request.getIngredients(), recipe);
        recipe.setIngredients(ingredients);
        recipe = recipeRepository.save(recipe);
        return RecipeMapper.toResponse(recipe);
    }

    @Transactional
    public void delete(Integer recipeId, User author) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new NotFoundException("recipe not found"));
        if (!recipe.getUser().getId().equals(author.getId())) {
            throw new NotFoundException("recipe not found for user");
        }
        recipeRepository.delete(recipe);
    }

    @Transactional
    public RecipeResponse changeStatus(Integer recipeId, RecipeStatus status, User author) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new NotFoundException("recipe not found"));
        if (!recipe.getUser().getId().equals(author.getId())) {
            throw new NotFoundException("recipe not found for user");
        }
        recipe.setStatus(status);
        recipe.setUpdatedAt(OffsetDateTime.now());
        recipe = recipeRepository.save(recipe);
        return RecipeMapper.toResponse(recipe);
    }

    private void applyRecipeFields(Recipe recipe, RecipeCreateRequest request, User author) {
        recipe.setUser(author);
        recipe.setTitle(request.getTitle());
        recipe.setDescription(request.getDescription());
        recipe.setInstructions(request.getInstructions());
        recipe.setPreparationTime(request.getPreparationTime());
        recipe.setCookingTime(request.getCookingTime());
        recipe.setDifficultyLevel(request.getDifficultyLevel());
        recipe.setServings(request.getServings());
        recipe.setImageUrl(request.getImageUrl());
        recipe.setIsPublic(request.getIsPublic());
        recipe.setStatus(RecipeStatus.ACTIVE);
        recipe.setUpdatedAt(OffsetDateTime.now());
        if (recipe.getCreatedAt() == null) {
            recipe.setCreatedAt(OffsetDateTime.now());
        }

        if (request.getCategoryId() != null) {
            RecipeCategory category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new NotFoundException("recipe category not found"));
            recipe.setCategory(category);
        } else {
            recipe.setCategory(null);
        }
    }

    private Set<RecipeIngredient> buildIngredients(List<RecipeIngredientRequest> ingredientRequests, Recipe recipe) {
        if (ingredientRequests == null || ingredientRequests.isEmpty()) {
            return Set.of();
        }
        Set<RecipeIngredient> result = new HashSet<>();
        for (RecipeIngredientRequest req : ingredientRequests) {
            Ingredient ingredient = ingredientService.findEntity(req.getIngredientId());
            RecipeIngredient ri = RecipeIngredient.builder()
                    .recipe(recipe)
                    .ingredient(ingredient)
                    .quantity(req.getQuantity())
                    .notes(req.getNotes())
                    .orderIndex(req.getOrderIndex())
                    .build();
            if (req.getUnitId() != null) {
                ri.setUnit(unitService.findEntity(req.getUnitId()));
            }
            result.add(ri);
        }
        return result;
    }
}
