package com.coolinaa.enums;

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
