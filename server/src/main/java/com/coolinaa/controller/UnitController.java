package com.coolinaa.controller;

import com.coolinaa.dto.response.UnitResponse;
import com.coolinaa.entity.Unit;
import com.coolinaa.service.UnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Контроллер для управления единицами измерения (граммы, литры, штуки и т.д.).
 * Справочник используется при создании рецептов для указания количества ингредиентов.
 */
@RestController
@RequestMapping("/api/v1/units")
@RequiredArgsConstructor
public class UnitController {

    private final UnitService unitService;

    /**
     * Возвращает полный список доступных единиц измерения.
     * @return Список {@link UnitResponse}.
     */
    @GetMapping
    public ResponseEntity<List<UnitResponse>> list() {
        return ResponseEntity.ok(unitService.findAll());
    }

    /**
     * Создает новую единицу измерения.
     *
     * @param body Map с параметрами: name (название), abbreviation (сокращение), isMetric (метрическая ли система).
     * @return Созданная единица измерения.
     */
    @PostMapping
    public ResponseEntity<UnitResponse> create(@RequestBody Map<String, Object> body) {
        Unit unit = Unit.builder()
                .name((String) body.get("name"))
                .abbreviation((String) body.get("abbreviation"))
                .isMetric(body.get("isMetric") == null ? Boolean.TRUE : (Boolean) body.get("isMetric"))
                .build();
        return ResponseEntity.ok(unitService.create(unit));
    }

    /**
     * Обновляет параметры существующей единицы измерения.
     *
     * @param id ID обновляемой записи.
     * @param body Map с новыми значениями полей. Поля со значением null игнорируются или затираются в зависимости от логики сервиса.
     * @return Обновленная запись.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UnitResponse> update(@PathVariable Integer id, @RequestBody Map<String, Object> body) {
        Unit payload = Unit.builder()
                .name((String) body.get("name"))
                .abbreviation((String) body.get("abbreviation"))
                .isMetric(body.get("isMetric") == null ? null : (Boolean) body.get("isMetric"))
                .build();
        return ResponseEntity.ok(unitService.update(id, payload));
    }

    /**
     * Удаляет единицу измерения из справочника.
     * @param id ID для удаления.
     * @return 204 No Content.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        unitService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
