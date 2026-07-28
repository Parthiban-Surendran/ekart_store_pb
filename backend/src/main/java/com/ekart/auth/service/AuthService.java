package com.ekart.auth.service;

import com.ekart.auth.dto.LoginRequest;
import com.ekart.auth.dto.LoginResponse;
import com.ekart.auth.dto.RegisterRequest;

public interface AuthService {

    LoginResponse login(LoginRequest request);
    void register(RegisterRequest request);
}