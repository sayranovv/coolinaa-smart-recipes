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
public class UserResponse {

    private Integer id;
    private String username;
    private String email;
    private Boolean isActive;
    private OffsetDateTime createdAt;

}
