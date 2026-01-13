package com.coolinaa.mapper;

import com.coolinaa.dto.response.UnitResponse;
import com.coolinaa.entity.Unit;

/**
 * Простой маппер для преобразования единиц измерения {@link Unit} в DTO {@link UnitResponse}.
 */
public final class UnitMapper {
    private UnitMapper() {
    }

    public static UnitResponse toResponse(Unit unit) {
        if (unit == null) {
            return null;
        }
        return UnitResponse.builder()
                .id(unit.getId())
                .name(unit.getName())
                .abbreviation(unit.getAbbreviation())
                .isMetric(unit.getIsMetric())
                .createdAt(unit.getCreatedAt())
                .build();
    }
}
