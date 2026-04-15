package com.steamclone.api.modules.user.controller;

import com.steamclone.api.modules.user.dto.*;
import com.steamclone.api.modules.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/userprofile")
    public ResponseEntity<UserResponse> profile() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }

    @PatchMapping("{userID}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable UUID userID,
            @RequestBody @Valid UpdateUserRequest request) {
        return ResponseEntity.ok(userService.updateProfile(userID,request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{userID}/role")
    public ResponseEntity<UserResponse> updateRole(
            @PathVariable UUID userID,
            @RequestBody @Valid UpdateUserRoleRequest request) {
        return ResponseEntity.ok(userService.updateRole(userID,request));
    }
}
