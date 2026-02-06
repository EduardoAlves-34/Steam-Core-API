package com.steamclone.api.modules.user.mapper;

import com.steamclone.api.modules.user.dto.UserResponse;
import com.steamclone.api.modules.user.entity.User;

public class UserMapper {

    private UserMapper() {}

    public static UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.getActive()
        );
    }
}
