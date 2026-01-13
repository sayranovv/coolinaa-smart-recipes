package com.coolinaa.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Конвертер JPA для автоматического преобразования {@link RecipeStatus} в строку и обратно
 * при сохранении в базу данных.
 * <p>
 * Позволяет хранить статусы в БД как строки в нижнем регистре ("draft", "active"),
 * а в коде работать с Java Enum.
 * </p>
 */
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
