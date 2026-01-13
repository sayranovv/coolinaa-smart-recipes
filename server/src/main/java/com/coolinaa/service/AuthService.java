package com.coolinaa.service;

import com.coolinaa.constant.ErrorMessages;
import com.coolinaa.dto.request.UserLoginRequest;
import com.coolinaa.dto.request.UserRegisterRequest;
import com.coolinaa.dto.response.AuthResponse;
import com.coolinaa.dto.response.UserResponse;
import com.coolinaa.entity.User;
import com.coolinaa.exception.ConflictException;
import com.coolinaa.exception.UnauthorizedException;
import com.coolinaa.mapper.UserMapper;
import com.coolinaa.repository.UserRepository;
import com.coolinaa.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

/**
 * Сервис аутентификации и регистрации пользователей.
 * Отвечает за проверку учетных данных, создание новых аккаунтов и выдачу JWT токенов.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Регистрирует нового пользователя.
     * Проверяет уникальность имени пользователя и email перед сохранением.
     *
     * @param request данные для регистрации (логин, email, пароль)
     * @return токены доступа и информация о созданном пользователе
     * @throws ConflictException если пользователь с таким именем или email уже существует
     */
    public AuthResponse register(UserRegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ConflictException(ErrorMessages.USERNAME_ALREADY_EXISTS);
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException(ErrorMessages.EMAIL_ALREADY_EXISTS);
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .createdAt(OffsetDateTime.now())
                .isActive(true)
                .build();
        userRepository.save(user);

        return buildTokens(user.getUsername(), UserMapper.toResponse(user));
    }

    /**
     * Выполняет вход пользователя в систему по логину/email и паролю.
     *
     * @param request учетные данные
     * @return токены доступа и информация о пользователе
     * @throws UnauthorizedException если учетные данные неверны
     */
    public AuthResponse login(UserLoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmailOrUsername(), request.getPassword())
        );

        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .or(() -> userRepository.findByEmail(username))
                .orElseThrow(() -> new UnauthorizedException(ErrorMessages.INVALID_CREDENTIALS));

        return buildTokens(user.getUsername(), UserMapper.toResponse(user));
    }

    /**
     * Обновляет Access-токен с помощью валидного Refresh-токена.
     *
     * @param refreshToken токен обновления
     * @return новая пара токенов
     * @throws UnauthorizedException если токен невалиден или пользователь не найден
     */
    public AuthResponse refresh(String refreshToken) {
        if (!jwtTokenProvider.validateRefreshToken(refreshToken)) {
            throw new UnauthorizedException(ErrorMessages.INVALID_JWT);
        }
        String username = jwtTokenProvider.getUsernameFromRefreshToken(refreshToken);
        User user = userRepository.findByUsername(username)
                .or(() -> userRepository.findByEmail(username))
                .orElseThrow(() -> new UnauthorizedException(ErrorMessages.USER_NOT_FOUND));

        return buildTokens(username, UserMapper.toResponse(user));
    }

    /**
     * Вспомогательный метод для генерации пары токенов (access + refresh).
     */
    private AuthResponse buildTokens(String username, UserResponse user) {
        String access = jwtTokenProvider.generateAccessToken(username, "ROLE_USER");
        String refresh = jwtTokenProvider.generateRefreshToken(username);

        return AuthResponse.builder()
                .accessToken(access)
                .refreshToken(refresh)
                .tokenType("Bearer")
                .expiresIn(jwtTokenProvider.getAccessExpirationMs())
                .refreshExpiresIn(jwtTokenProvider.getRefreshExpirationMs())
                .user(user)
                .build();
    }

    /**
     * Получает информацию о текущем пользователе по его имени (из токена).
     *
     * @param username имя пользователя
     * @return DTO пользователя
     */
    public UserResponse currentUser(String username) {
        User user = userRepository.findByUsername(username)
                .or(() -> userRepository.findByEmail(username))
                .orElseThrow(() -> new UnauthorizedException(ErrorMessages.USER_NOT_FOUND));
        return UserMapper.toResponse(user);
    }
}
