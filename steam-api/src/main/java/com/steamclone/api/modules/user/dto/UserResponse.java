package com.steamclone.api.modules.user.dto;

import com.steamclone.api.modules.user.entity.Role;

import java.util.UUID;

public record UserResponse(
        UUID id,
        String username,
        String email,
        Role role,
        Boolean active
) {}
