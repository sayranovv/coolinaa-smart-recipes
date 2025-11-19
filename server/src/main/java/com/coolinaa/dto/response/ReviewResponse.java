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
public class ReviewResponse {

    private Integer id;
    private Integer rating;
    private String comment;
    private UserResponse author;
    private Integer recipeId;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

}
