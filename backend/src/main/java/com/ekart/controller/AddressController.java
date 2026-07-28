package com.ekart.controller;

import com.ekart.common.dto.AddressRequest;
import com.ekart.common.dto.AddressResponse;
import com.ekart.common.response.ApiResponse;
import com.ekart.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<AddressResponse> addAddress(
            @Valid @RequestBody AddressRequest request) {

        AddressResponse response = addressService.addAddress(request);

        return ApiResponse.<AddressResponse>builder()
                .success(true)
                .message("Address added successfully")
                .data(response)
                .build();
    }

    @GetMapping
    public ApiResponse<List<AddressResponse>> getMyAddresses() {

        List<AddressResponse> response =
                addressService.getMyAddresses();

        return ApiResponse.<List<AddressResponse>>builder()
                .success(true)
                .message("Addresses fetched successfully")
                .data(response)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<AddressResponse> getAddress(
            @PathVariable Long id) {

        AddressResponse response =
                addressService.getAddress(id);

        return ApiResponse.<AddressResponse>builder()
                .success(true)
                .message("Address fetched successfully")
                .data(response)
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<AddressResponse> updateAddress(
            @PathVariable Long id,
            @Valid @RequestBody AddressRequest request) {

        AddressResponse response =
                addressService.updateAddress(id, request);

        return ApiResponse.<AddressResponse>builder()
                .success(true)
                .message("Address updated successfully")
                .data(response)
                .build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Void> deleteAddress(
            @PathVariable Long id) {

        addressService.deleteAddress(id);

        return ApiResponse.<Void>builder()
                .success(true)
                .message("Address deleted successfully")
                .build();
    }

}