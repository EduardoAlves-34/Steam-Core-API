package com.steamclone.api.modules.auth.controller;

import com.steamclone.api.modules.auth.dto.*;
import com.steamclone.api.modules.auth.service.AuthService;
import com.steamclone.api.modules.user.dto.UserRegisterRequest;
import com.steamclone.api.modules.user.dto.UserResponse;
import com.steamclone.api.modules.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
            @RequestBody @Valid UserRegisterRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody @Valid AuthRequest request) {

        return ResponseEntity.ok(authService.login(request));
    }
}
