package com.coolinaa.service;

import com.coolinaa.constant.ErrorMessages;
import com.coolinaa.dto.request.UserLoginRequest;
import com.coolinaa.dto.request.UserRegisterRequest;
import com.coolinaa.dto.response.AuthResponse;
import com.coolinaa.dto.response.UserResponse;
import com.coolinaa.entity.User;
import com.coolinaa.exception.ConflictException;
import com.coolinaa.exception.UnauthorizedException;
import com.coolinaa.repository.UserRepository;
import com.coolinaa.security.jwt.JwtTokenProvider;
import com.coolinaa.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

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

    public UserResponse currentUser(String username) {
        User user = userRepository.findByUsername(username)
                .or(() -> userRepository.findByEmail(username))
                .orElseThrow(() -> new UnauthorizedException(ErrorMessages.USER_NOT_FOUND));
        return UserMapper.toResponse(user);
    }
}
