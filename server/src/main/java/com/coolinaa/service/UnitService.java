package com.coolinaa.service;

import com.coolinaa.dto.response.UnitResponse;
import com.coolinaa.entity.Unit;
import com.coolinaa.exception.ConflictException;
import com.coolinaa.exception.NotFoundException;
import com.coolinaa.mapper.UnitMapper;
import com.coolinaa.repository.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UnitService {

    private final UnitRepository unitRepository;

    public List<UnitResponse> findAll() {
        return unitRepository.findAll().stream()
                .map(UnitMapper::toResponse)
                .collect(Collectors.toList());
    }

    public UnitResponse create(Unit unit) {
        if (unitRepository.existsByName(unit.getName())) {
            throw new ConflictException("unit already exists");
        }
        if (unit.getCreatedAt() == null) {
            unit.setCreatedAt(OffsetDateTime.now());
        }
        return UnitMapper.toResponse(unitRepository.save(unit));
    }

    public Unit findEntity(Integer id) {
        return unitRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("unit not found"));
    }
}
