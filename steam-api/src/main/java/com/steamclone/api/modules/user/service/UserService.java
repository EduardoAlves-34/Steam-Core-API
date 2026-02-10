package com.steamclone.api.modules.user.service;

import com.steamclone.api.modules.user.dto.UpdateUserRequest;
import com.steamclone.api.modules.user.dto.UpdateUserRoleRequest;
import com.steamclone.api.modules.user.dto.UserRegisterRequest;
import com.steamclone.api.modules.user.dto.UserResponse;

import java.util.UUID;

public interface UserService {

    UserResponse register(UserRegisterRequest request);

    UserResponse getCurrentUser();
    UserResponse updateProfile(UpdateUserRequest request);
    UserResponse updateRole(UUID userId, UpdateUserRoleRequest request);
}
