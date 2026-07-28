package com.ekart.auth.controller;

import com.ekart.auth.dto.LoginRequest;
import com.ekart.auth.dto.LoginResponse;
import com.ekart.auth.dto.RegisterRequest;
import com.ekart.auth.service.AuthService;
import com.ekart.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {

        LoginResponse response = authService.login(request);

        return ApiResponse.<LoginResponse>builder()
                .success(true)
                .message("Login successful")
                .data(response)
                .build();
    }


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<?> register(
            @Valid @RequestBody RegisterRequest request) {

        authService.register(request);

        return ApiResponse.builder()
                .success(true)
                .message("Registration successful")
                .build();
    }
}