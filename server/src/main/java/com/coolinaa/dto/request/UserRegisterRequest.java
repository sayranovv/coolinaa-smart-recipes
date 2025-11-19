package com.coolinaa.dto.request;

import jakarta.validation.constraints.Email;
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
public class UserRegisterRequest {

    @NotBlank(message = "username must be not empty")
    @Size(min = 3, max = 50, message = "username must be between 3 and 50 characters long")
    private String username;

    @NotBlank(message = "email must be not empty")
    @Email(message = "email must be valid")
    private String email;

    @NotBlank(message = "password must be not empty")
    @Size(min = 8, message = "password must be min 8 characters")
    private String password;

}
