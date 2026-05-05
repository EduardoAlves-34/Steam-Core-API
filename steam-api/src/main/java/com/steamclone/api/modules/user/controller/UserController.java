package com.steamclone.api.modules.user.controller;

import com.steamclone.api.modules.user.dto.*;
import com.steamclone.api.modules.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> profile() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable UUID userId,
            @RequestBody @Valid UpdateUserRequest request) {
        return ResponseEntity.ok(userService.updateProfile(userId, request));
    }
}
