package com.ekart.service;

import com.ekart.common.dto.UserRequest;
import com.ekart.common.dto.UserResponse;
import com.ekart.common.dto.UserProfileResponse;

public interface UserService {

    UserResponse createUser(UserRequest request);

    UserProfileResponse getCurrentUser(String email);
}