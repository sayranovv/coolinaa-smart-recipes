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
public class UnitResponse {

    private Integer id;
    private String name;
    private String abbreviation;
    private Boolean isMetric;
    private OffsetDateTime createdAt;

}
