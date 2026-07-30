package com.ekart.common.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateOrderStatusRequest {

    @NotBlank
    private String status;
}