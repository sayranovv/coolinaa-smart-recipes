package com.coolinaa.controller;

import com.coolinaa.dto.response.UnitResponse;
import com.coolinaa.entity.Unit;
import com.coolinaa.service.UnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
}
