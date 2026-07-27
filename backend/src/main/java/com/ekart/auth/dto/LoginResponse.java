package com.ekart.auth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}