package com.steamclone.api.modules.user.service.impl;

import com.steamclone.api.modules.user.dto.UserRegisterRequest;
import com.steamclone.api.modules.user.dto.UserResponse;
import com.steamclone.api.modules.user.entity.Role;
import com.steamclone.api.modules.user.entity.User;
import com.steamclone.api.modules.user.mapper.UserMapper;
import com.steamclone.api.modules.user.repository.UserRepository;
import com.steamclone.api.modules.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse register(UserRegisterRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email já cadastrado");
        }

        if (userRepository.existsByUsername(request.username())) {
            throw new RuntimeException("Username já cadastrado");
        }

        User user = User.builder()
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .active(true)
                .build();

        return UserMapper.toResponse(userRepository.save(user));
    }

    @Override
    public UserResponse getCurrentUser() {
        // vamos implementar depois com Spring Security + JWT
        throw new UnsupportedOperationException("Não implementado ainda");
    }
}
