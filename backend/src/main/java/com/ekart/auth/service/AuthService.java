package com.ekart.auth.service;

import com.ekart.auth.dto.LoginRequest;
import com.ekart.auth.dto.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);
}