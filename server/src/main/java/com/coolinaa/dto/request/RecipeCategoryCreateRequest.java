package com.coolinaa.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeCategoryCreateRequest {

    @NotBlank(message = "name must be not empty")
    @Size(min = 2, max = 50, message = "name must be between 2 and 50 characters long")
    private String name;

    @Size(max = 255, message = "description must be at most 255 characters")
    private String description;
}
