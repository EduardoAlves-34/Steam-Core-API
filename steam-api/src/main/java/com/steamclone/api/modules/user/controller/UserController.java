package com.steamclone.api.modules.user.controller;

import com.steamclone.api.modules.user.dto.*;
import com.steamclone.api.modules.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> me() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }

    @PutMapping("/me")
    public ResponseEntity<UserResponse> update(
            @RequestBody @Valid UpdateUserRequest request) {
        return ResponseEntity.ok(userService.updateProfile(request));
    }
}
