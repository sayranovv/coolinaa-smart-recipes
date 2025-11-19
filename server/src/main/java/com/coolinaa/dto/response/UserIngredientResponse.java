package com.coolinaa.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserIngredientResponse {

    private Integer id;
    private Integer ingredientId;
    private String ingredientName;
    private BigDecimal quantity;
    private Integer unitId;
    private String unitName;
    private OffsetDateTime addedAt;
    private OffsetDateTime expiresAt;
    private Boolean isExpired;

}
