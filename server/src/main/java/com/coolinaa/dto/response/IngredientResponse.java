package com.coolinaa.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IngredientResponse {

    private Integer id;
    private String name;
    private String description;
    private Boolean isActive;
    private Integer categoryId;
    private String categoryName;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

}
