package com.coolinaa.service;

import com.coolinaa.dto.request.UserIngredientRequest;
import com.coolinaa.dto.response.UserIngredientResponse;
import com.coolinaa.entity.Ingredient;
import com.coolinaa.entity.Unit;
import com.coolinaa.entity.User;
import com.coolinaa.entity.UserIngredient;
import com.coolinaa.exception.NotFoundException;
import com.coolinaa.mapper.UserIngredientMapper;
import com.coolinaa.repository.UserIngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервис для управления "Холодильником" пользователя.
 * Позволяет добавлять продукты в личный список и отслеживать их срок годности.
 */
@Service
@RequiredArgsConstructor
public class UserIngredientService {

    private final UserIngredientRepository userIngredientRepository;
    private final IngredientService ingredientService;
    private final UnitService unitService;

    /**
     * Постраничный список продуктов пользователя.
     */
    public Page<UserIngredientResponse> list(Integer userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userIngredientRepository.findByUser_Id(userId, pageable)
                .map(UserIngredientMapper::toResponse);
    }

    /**
     * Полный список продуктов (для алгоритмов подбора).
     */
    public List<UserIngredientResponse> listAll(Integer userId) {
        return userIngredientRepository.findByUser_Id(userId)
                .stream().map(UserIngredientMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Добавляет или обновляет продукт в холодильнике.
     * Если продукт уже есть, обновляет его количество и срок годности.
     */
    @Transactional
    public UserIngredientResponse add(User user, UserIngredientRequest request) {
        Ingredient ingredient = ingredientService.findEntity(request.getIngredientId());
        Unit unit = request.getUnitId() != null ? unitService.findEntity(request.getUnitId()) : null;

        UserIngredient userIngredient = userIngredientRepository.findByUser_IdAndIngredient_Id(user.getId(), ingredient.getId())
                .orElse(UserIngredient.builder().user(user).ingredient(ingredient).build());

        userIngredient.setQuantity(request.getQuantity());
        userIngredient.setUnit(unit);

        OffsetDateTime expiresAt = request.getExpiresAt() != null
                ? request.getExpiresAt().atStartOfDay(ZoneId.systemDefault()).toOffsetDateTime()
                : null;
        userIngredient.setExpiresAt(expiresAt);

        return UserIngredientMapper.toResponse(userIngredientRepository.save(userIngredient));
    }

    /**
     * Удаляет продукт из списка.
     */
    @Transactional
    public void delete(Integer userId, Integer ingredientId) {
        UserIngredient ui = userIngredientRepository.findByUser_IdAndIngredient_Id(userId, ingredientId)
                .orElseThrow(() -> new NotFoundException("user ingredient not found"));
        userIngredientRepository.delete(ui);
    }
}
