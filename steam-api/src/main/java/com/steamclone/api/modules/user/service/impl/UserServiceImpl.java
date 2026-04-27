package com.steamclone.api.modules.user.service.impl;

import com.steamclone.api.modules.user.dto.UpdateUserRequest;
import com.steamclone.api.modules.user.dto.UpdateUserRoleRequest;
import com.steamclone.api.modules.user.dto.UserRegisterRequest;
import com.steamclone.api.modules.user.dto.UserResponse;
import com.steamclone.api.modules.user.entity.User;
import com.steamclone.api.modules.user.enums.Role;
import com.steamclone.api.modules.user.mapper.UserMapper;
import com.steamclone.api.modules.user.repository.UserRepository;
import com.steamclone.api.modules.user.service.UserService;
import com.steamclone.api.shared.exception.BadRequestException;
import com.steamclone.api.shared.exception.BusinessException;
import com.steamclone.api.shared.exception.ForbiddenException;
import com.steamclone.api.shared.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.steamclone.api.modules.user.enums.Role.USER;



@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse register(UserRegisterRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new BusinessException("Email already exists");
        }

        Role role = request.role() != null
                ? Role.valueOf(request.role().toUpperCase())
                : USER;

        User user = User.builder()
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(role)
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
                        new ResourceNotFoundException("User not found"));

        return UserMapper.toResponse(user);
    }

    @Override
    @Transactional
    public UserResponse updateProfile(UUID userID, UpdateUserRequest request) {
        String authenticatedEmail = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User currentUser = userRepository.findByEmail(authenticatedEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Authenticated user not found"));

        boolean isAdmin = currentUser.getRole().equals(USER.ADMIN);
        boolean isProfileOwner = currentUser.getId().equals(userID);

        if (!isAdmin && !isProfileOwner) {
            throw new ForbiddenException("You do not have permission to update this profile");
        }

        User userToUpdate = isProfileOwner ? currentUser :
                userRepository.findById(userID)
                        .orElseThrow(() -> new ResourceNotFoundException("Target user not found"));

        if (request.username() != null) {
            String newUsername = request.username().trim();

            if (newUsername.equalsIgnoreCase(userToUpdate.getUsername())) {
                throw new BadRequestException("The new username must be different from the current one");
            }

            userToUpdate.setUsername(newUsername);
        }

        return UserMapper.toResponse(userRepository.save(userToUpdate));
    }

    @Transactional
    @Override
    public UserResponse updateRole(UUID userId, UpdateUserRoleRequest request) {
        String authenticatedEmail = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User currentUser = userRepository.findByEmail(authenticatedEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Authenticated user not found"));

        if (!currentUser.getRole().equals(Role.ADMIN)) {
            throw new ForbiddenException("Only administrators can change user roles");
        }

        User targetUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Target user not found"));

        if (request.role().equals(targetUser.getRole())) {
            throw new BadRequestException("The user already has the assigned role");
        }

        if (currentUser.getId().equals(targetUser.getId()) && request.role().equals(USER)) {
            throw new BadRequestException("You cannot demote yourself.");
        }

        targetUser.setRole(request.role());

        return UserMapper.toResponse(userRepository.save(targetUser));
    }

}
