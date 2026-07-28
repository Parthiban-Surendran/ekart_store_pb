package com.ekart.service;

import com.ekart.common.dto.ProductRequest;
import com.ekart.common.dto.ProductResponse;

public interface ProductService {

    ProductResponse createProduct(ProductRequest request);

}