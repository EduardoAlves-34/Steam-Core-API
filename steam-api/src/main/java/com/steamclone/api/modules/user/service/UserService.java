package com.steamclone.api.modules.user.service;

import com.steamclone.api.modules.user.dto.UserRegisterRequest;
import com.steamclone.api.modules.user.dto.UserResponse;

public interface UserService {

    UserResponse register(UserRegisterRequest request);

    UserResponse getCurrentUser();
}
