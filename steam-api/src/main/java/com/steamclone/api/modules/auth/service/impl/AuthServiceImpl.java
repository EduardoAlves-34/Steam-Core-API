package com.steamclone.api.modules.auth.service.impl;

import com.steamclone.api.modules.auth.dto.AuthRequest;
import com.steamclone.api.modules.auth.dto.AuthResponse;
import com.steamclone.api.modules.auth.service.AuthService;
import com.steamclone.api.shared.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public AuthResponse login(AuthRequest request) {


        var authenticationToken = new UsernamePasswordAuthenticationToken(
                request.email(),
                request.password()
        );


        var authentication = authenticationManager.authenticate(authenticationToken);

        var userDetails = (UserDetails) authentication.getPrincipal();

        String token = jwtUtil.generateToken(userDetails);

        return new AuthResponse(token);
    }
}
