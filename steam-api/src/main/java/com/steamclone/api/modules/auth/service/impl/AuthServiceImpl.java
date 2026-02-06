package com.steamclone.api.modules.auth.service.impl;

import com.steamclone.api.modules.auth.dto.AuthRequest;
import com.steamclone.api.modules.auth.dto.AuthResponse;
import com.steamclone.api.modules.auth.service.AuthService;
import com.steamclone.api.shared.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public AuthResponse login(AuthRequest request) {

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                );

        authenticationManager.authenticate(authentication);

        String token = jwtUtil.generateToken(request.email());

        return new AuthResponse(token);
    }
}
