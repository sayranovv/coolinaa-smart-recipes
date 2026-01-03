package com.coolinaa.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RecipeStatusConverter implements AttributeConverter<RecipeStatus, String> {
    @Override
    public String convertToDatabaseColumn(RecipeStatus status) {
        return status == null ? null : status.getCode();
    }

    @Override
    public RecipeStatus convertToEntityAttribute(String dbData) {
        return RecipeStatus.fromCode(dbData);
    }
}
