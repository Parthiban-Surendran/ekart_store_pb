package com.ekart.service;

import com.ekart.common.dto.UserRequest;
import com.ekart.common.dto.UserResponse;

public interface UserService {

    UserResponse createUser(UserRequest request);
}