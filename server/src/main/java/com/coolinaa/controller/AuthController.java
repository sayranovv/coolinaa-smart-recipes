package com.coolinaa.controller;

import com.coolinaa.dto.request.RefreshTokenRequest;
import com.coolinaa.dto.request.UserLoginRequest;
import com.coolinaa.dto.request.UserRegisterRequest;
import com.coolinaa.dto.response.AuthResponse;
import com.coolinaa.dto.response.UserResponse;
import com.coolinaa.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * REST-контроллер для аутентификации и регистрации пользователей.
 * Предоставляет публичные точки доступа для получения JWT токенов.
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Регистрирует нового пользователя в системе.
     *
     * @param request объект {@link UserRegisterRequest} с данными для регистрации (email, пароль, имя)
     * @return {@link AuthResponse} содержащий access и refresh токены
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody UserRegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    /**
     * Выполняет вход пользователя в систему.
     *
     * @param request объект {@link UserLoginRequest} с учетными данными
     * @return {@link AuthResponse} содержащий access и refresh токены
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody UserLoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    /**
     * Обновляет access токен, используя валидный refresh токен.
     *
     * @param request запрос, содержащий refresh токен
     * @return новый пару токенов
     */
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@Valid @RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(authService.refresh(request.getRefreshToken()));
    }

    /**
     * Получает информацию о текущем аутентифицированном пользователе.
     *
     * @return {@link UserResponse} с данными пользователя или 401, если пользователь не найден
     */
    @GetMapping("/me")
    public ResponseEntity<UserResponse> me() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(authService.currentUser(authentication.getName()));
    }
}
