package com.ekart.controller;

import com.ekart.common.dto.ProductRequest;
import com.ekart.common.dto.ProductResponse;
import com.ekart.common.response.ApiResponse;
import com.ekart.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(
            @Valid @RequestBody ProductRequest request) {

        ProductResponse response = productService.createProduct(request);

        return ResponseEntity.ok(
                ApiResponse.<ProductResponse>builder()
                        .success(true)
                        .message("Product created successfully")
                        .data(response)
                        .build()
        );
    }
}