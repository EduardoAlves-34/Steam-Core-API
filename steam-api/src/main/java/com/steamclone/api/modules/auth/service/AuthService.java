package com.steamclone.api.modules.auth.service;

import com.steamclone.api.modules.auth.dto.AuthRequest;
import com.steamclone.api.modules.auth.dto.AuthResponse;

public interface AuthService {
    AuthResponse login(AuthRequest request);
}
