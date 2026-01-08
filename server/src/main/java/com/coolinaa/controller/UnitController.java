package com.coolinaa.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coolinaa.dto.response.UnitResponse;
import com.coolinaa.entity.Unit;
import com.coolinaa.service.UnitService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/units")
@RequiredArgsConstructor
public class UnitController {

    private final UnitService unitService;

    @GetMapping
    public ResponseEntity<List<UnitResponse>> list() {
        return ResponseEntity.ok(unitService.findAll());
    }

    @PostMapping
    public ResponseEntity<UnitResponse> create(@RequestBody Map<String, Object> body) {
        Unit unit = Unit.builder()
                .name((String) body.get("name"))
                .abbreviation((String) body.get("abbreviation"))
                .isMetric(body.get("isMetric") == null ? Boolean.TRUE : (Boolean) body.get("isMetric"))
                .build();
        return ResponseEntity.ok(unitService.create(unit));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UnitResponse> update(@PathVariable Integer id, @RequestBody Map<String, Object> body) {
        Unit payload = Unit.builder()
                .name((String) body.get("name"))
                .abbreviation((String) body.get("abbreviation"))
                .isMetric(body.get("isMetric") == null ? null : (Boolean) body.get("isMetric"))
                .build();
        return ResponseEntity.ok(unitService.update(id, payload));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        unitService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
