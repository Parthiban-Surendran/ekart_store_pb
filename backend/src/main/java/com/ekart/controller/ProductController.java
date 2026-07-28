package com.ekart.controller;

import com.ekart.common.dto.ProductRequest;
import com.ekart.common.dto.ProductResponse;
import com.ekart.common.response.ApiResponse;
import com.ekart.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ProductResponse>>> getProducts(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(defaultValue = "id")
            String sortBy,

            @RequestParam(defaultValue = "asc")
            String direction,

            @RequestParam(required = false)
            String keyword) {

        Page<ProductResponse> response =
                productService.getAllProducts(
                        page,
                        size,
                        sortBy,
                        direction,
                        keyword);

        return ResponseEntity.ok(
                ApiResponse.<Page<ProductResponse>>builder()
                        .success(true)
                        .message("Products fetched successfully")
                        .data(response)
                        .build()
        );
    }

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