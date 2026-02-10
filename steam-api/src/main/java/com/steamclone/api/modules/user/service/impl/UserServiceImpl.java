package com.steamclone.api.modules.user.service.impl;

import com.steamclone.api.modules.user.dto.UpdateUserRequest;
import com.steamclone.api.modules.user.dto.UpdateUserRoleRequest;
import com.steamclone.api.modules.user.dto.UserRegisterRequest;
import com.steamclone.api.modules.user.dto.UserResponse;
import com.steamclone.api.modules.user.entity.Role;
import com.steamclone.api.modules.user.entity.User;
import com.steamclone.api.modules.user.mapper.UserMapper;
import com.steamclone.api.modules.user.repository.UserRepository;
import com.steamclone.api.modules.user.service.UserService;
import com.steamclone.api.shared.exception.BusinessException;
import com.steamclone.api.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse register(UserRegisterRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new BusinessException("Email já cadastrado");
        }

        if (userRepository.existsByEmail(request.email())) {
            throw new BusinessException("Email já cadastrado");
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

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuário não encontrado"));

        return UserMapper.toResponse(user);
    }

    @Override
    public UserResponse updateProfile(UpdateUserRequest request) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuário não encontrado"));

        user.setUsername(request.username());

        return UserMapper.toResponse(userRepository.save(user));
    }

    @Override
    public UserResponse updateRole(UUID userId, UpdateUserRoleRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuário não encontrado"));

        user.setRole(request.role());

        return UserMapper.toResponse(userRepository.save(user));
    }

}
