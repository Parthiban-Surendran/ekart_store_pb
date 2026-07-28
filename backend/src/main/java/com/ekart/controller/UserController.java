package com.ekart.controller;

import com.ekart.common.dto.UserRequest;
import com.ekart.common.dto.UserResponse;
import com.ekart.common.response.ApiResponse;
import com.ekart.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.ekart.common.dto.UserProfileResponse;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getCurrentUser(
            Authentication authentication) {

        UserProfileResponse response =
                userService.getCurrentUser(authentication.getName());

        return ResponseEntity.ok(
                ApiResponse.<UserProfileResponse>builder()
                        .success(true)
                        .message("User profile fetched successfully")
                        .data(response)
                        .build()
        );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)

    public ApiResponse<UserResponse> createUser(@Valid @RequestBody UserRequest request) {

        UserResponse response = userService.createUser(request);

        return ApiResponse.<UserResponse>builder()
                .success(true)
                .message("User created successfully")
                .data(response)
                .build();
    }
}