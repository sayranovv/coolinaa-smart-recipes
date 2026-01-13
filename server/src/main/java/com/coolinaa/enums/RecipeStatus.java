package com.coolinaa.enums;

/**
 * Статусы жизненного цикла рецепта.
 * Используются для управления видимостью и доступностью рецепта.
 */
public enum RecipeStatus {
    DRAFT("draft"),
    ACTIVE("active"),
    ARCHIVED("archived");

    private final String code;

    RecipeStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    /**
     * Преобразует строковый код статуса в элемент перечисления.
     * @param code код статуса (например, "active")
     * @return соответствующий {@link RecipeStatus}
     * @throws IllegalArgumentException если код неизвестен
     */
    public static RecipeStatus fromCode(String code) {
        if (code == null) {
            return null;
        }
        for (RecipeStatus value : RecipeStatus.values()) {
            if (value.code.equalsIgnoreCase(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Unknown recipe status: " + code);
    }
}
