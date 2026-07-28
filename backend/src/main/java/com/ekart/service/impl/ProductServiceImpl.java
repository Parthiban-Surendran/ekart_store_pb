package com.ekart.service.impl;

import com.ekart.common.dto.ProductRequest;
import com.ekart.common.dto.ProductResponse;
import com.ekart.common.entity.Product;
import com.ekart.repository.ProductRepository;
import com.ekart.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductResponse createProduct(ProductRequest request) {

        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock())
                .imageUrl(request.getImageUrl())
                .active(true)
                .build();

        Product saved = productRepository.save(product);

        return ProductResponse.builder()
                .id(saved.getId())
                .name(saved.getName())
                .description(saved.getDescription())
                .price(saved.getPrice())
                .stock(saved.getStock())
                .imageUrl(saved.getImageUrl())
                .active(saved.getActive())
                .build();
    }
}