package com.medical.medical_chekup.restcontroller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medical.medical_chekup.dto.PasienCustomerDTO;
import com.medical.medical_chekup.dto.PasienCustomerResDTO;
import com.medical.medical_chekup.dto.response.ApiResponse;
import com.medical.medical_chekup.dto.response.ApiResponsePagination;
import com.medical.medical_chekup.model.MCustomer;
import com.medical.medical_chekup.service.PasienService;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/pasien")
@RequiredArgsConstructor
public class ApiPasienController {

    private final PasienService pasienService;

    @PostMapping("/create/")
    public ResponseEntity<ApiResponse<?>> createPasien(@RequestBody PasienCustomerDTO pasienCustomerDTO) {

        MCustomer response = pasienService.createCustomer(pasienCustomerDTO);
        ApiResponse<?> apiResponse = new ApiResponse<>(
                "succes create pasien ", response, LocalDateTime.now(), HttpStatus.OK.value());
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponsePagination<?>> getAllDataPasien(@RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "1") Integer current, @RequestParam(defaultValue = "10") Integer size) {
        ApiResponsePagination<?> response = pasienService.getAllCustomer(keyword, current, size);

        return ResponseEntity.ok(response);

    }

    @PutMapping("edit/{customerId}")
    public ResponseEntity<ApiResponse<?>> updateDataPasien(@PathVariable Long customerId,
            @RequestBody PasienCustomerDTO pasienCustomerDTO) {
        MCustomer response = pasienService.editCustomer(customerId, pasienCustomerDTO);
        ApiResponse<?> apiResponse = new ApiResponse<>(
                "succces edit customer", response, LocalDateTime.now(), HttpStatus.OK.value());
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/batch")
    public ResponseEntity<ApiResponse<?>> deleteMultiplePasien(@RequestBody List<Long> customerIds) {
        pasienService.deleteMultipleCustomer(customerIds);
        ApiResponse<?> apiResponse = new ApiResponse<>(
                "success delete multiple customers", null, LocalDateTime.now(), HttpStatus.OK.value());
        return ResponseEntity.ok(apiResponse);
    }

}
