package com.coolinaa.security;

import com.coolinaa.entity.User;
import com.coolinaa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Сервис для загрузки данных пользователя из базы данных.
 * Реализует {@link UserDetailsService}, необходимый для Spring Security AuthenticationManager.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Загружает пользователя по имени или email.
     * Используется при аутентификации.
     *
     * @param usernameOrEmail логин или почта пользователя
     * @return объект {@link CustomUserDetails}
     * @throws UsernameNotFoundException если пользователь не найден
     */
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(usernameOrEmail)
                .or(() -> userRepository.findByEmail(usernameOrEmail))
                .orElseThrow(() -> {
                    log.warn("user not found: {}", usernameOrEmail);
                    return new UsernameNotFoundException("user not found");
                });

        return new CustomUserDetails(user, user.getRole());
    }

    /**
     * Загружает пользователя по ID.
     * Полезно для внутренних проверок или обновления токена.
     *
     * @param userId ID пользователя
     * @return объект {@link CustomUserDetails}
     */
    public UserDetails loadUserById(Integer userId) throws UsernameNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("user not found"));
        return new CustomUserDetails(user, user.getRole());
    }
}
