package com.steamclone.api.modules.user.dto;

import com.steamclone.api.modules.user.enums.Role;
import jakarta.validation.constraints.NotNull;

public record UpdateUserRoleRequest(
        @NotNull
        Role role
) {}
