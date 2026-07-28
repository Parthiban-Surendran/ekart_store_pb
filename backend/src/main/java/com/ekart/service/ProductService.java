package com.ekart.service;

import com.ekart.common.dto.ProductRequest;
import com.ekart.common.dto.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Page;


public interface ProductService {

    ProductResponse createProduct(ProductRequest request);

    ProductResponse getProductById(Long id);

    Page<ProductResponse> getAllProducts(
            int page,
            int size,
            String sortBy,
            String direction,
            String keyword
    );

}