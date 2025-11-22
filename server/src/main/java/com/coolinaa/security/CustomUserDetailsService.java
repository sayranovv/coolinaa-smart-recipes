package com.coolinaa.security;

import com.coolinaa.entity.User;
import com.coolinaa.enums.UserRole;
import com.coolinaa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(usernameOrEmail)
                .or(() -> userRepository.findByEmail(usernameOrEmail))
                .orElseThrow(() -> {
                    log.warn("user not found: {}", usernameOrEmail);
                    return new UsernameNotFoundException("user not found");
                });

        UserRole role = UserRole.USER; // temp

        return new CustomUserDetails(user, role);
    }

    public UserDetails loadUserById(Long userId) throws UsernameNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("user not found"));

        UserRole role = UserRole.USER; // temp
        return new CustomUserDetails(user, role);
    }
}
