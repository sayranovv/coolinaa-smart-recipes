package com.coolinaa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import com.coolinaa.security.CustomUserDetailsService;
import com.coolinaa.security.jwt.JwtAuthenticationFilter;
import com.coolinaa.security.jwt.JwtTokenProvider;
import com.coolinaa.security.jwt.RestAccessDeniedHandler;
import com.coolinaa.security.jwt.RestAuthenticationEntryPoint;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

/**
 * Основная конфигурация безопасности Spring Security.
 * <p>
 * Класс настраивает цепочку фильтров безопасности (SecurityFilterChain),
 * управление сессиями (stateless для JWT), CORS и правила авторизации HTTP-запросов.
 * </p>
 */

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;

    /**
     * Определяет цепочку фильтров безопасности.
     *
     * <ul>
     *   <li>Отключает CSRF (так как используются JWT токены).</li>
     *   <li>Устанавливает Stateless управление сессиями.</li>
     *   <li>Настраивает доступ к публичным эндпоинтам (auth, health, swagger, GET recipes).</li>
     *   <li>Добавляет {@link JwtAuthenticationFilter} перед стандартным фильтром аутентификации.</li>
     * </ul>
     *
     * @param http объект конфигурации HttpSecurity
     * @return сконфигурированная цепочка фильтров
     * @throws Exception при ошибках конфигурации безопасности
     */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> {})
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/v1/auth/**",
                                "/api/v1/health/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html",
                                "/swagger-ui/**"
                        ).permitAll()
                        .requestMatchers("GET", "/api/v1/recipes").permitAll()
                        .requestMatchers("GET", "/api/v1/recipes/**").permitAll()
                        .requestMatchers("GET", "/api/v1/ingredients").permitAll()
                        .requestMatchers("GET", "/api/v1/units").permitAll()
                        .requestMatchers("GET", "/api/v1/recipe-categories").permitAll()
                        .requestMatchers("GET", "/api/v1/ingredient-categories").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(handlers -> handlers
                        .authenticationEntryPoint(restAuthenticationEntryPoint())
                        .accessDeniedHandler(restAccessDeniedHandler())
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout
                        .logoutUrl("/api/v1/auth/logout")
                        .addLogoutHandler(logoutHandler())
                        .logoutSuccessHandler(logoutSuccessHandler())
                );
        return http.build();
    }

    /**
     * Создает провайдер аутентификации, использующий DAO для поиска пользователей
     * и BCrypt для проверки паролей.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtTokenProvider, customUserDetailsService);
    }

    @Bean
    public RestAuthenticationEntryPoint restAuthenticationEntryPoint() {
        return new RestAuthenticationEntryPoint(objectMapper);
    }

    @Bean
    public RestAccessDeniedHandler restAccessDeniedHandler() {
        return new RestAccessDeniedHandler(objectMapper);
    }

    @Bean
    public LogoutHandler logoutHandler() {
        return new SecurityContextLogoutHandler();
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new SimpleUrlLogoutSuccessHandler();
    }

    @Bean
    public org.springframework.web.cors.CorsConfigurationSource corsConfigurationSource() {
        var config = new org.springframework.web.cors.CorsConfiguration();
        config.setAllowedOriginPatterns(java.util.List.of("*"));
        config.setAllowedMethods(java.util.List.of("GET","POST","PUT","PATCH","DELETE","OPTIONS"));
        config.setAllowedHeaders(java.util.List.of("Authorization","Content-Type","X-Requested-With","Origin","Accept"));
        config.setExposedHeaders(java.util.List.of("Authorization","Content-Type"));
        config.setAllowCredentials(false);
        var source = new org.springframework.web.cors.UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
