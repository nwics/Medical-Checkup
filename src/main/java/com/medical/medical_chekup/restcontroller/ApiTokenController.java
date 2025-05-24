package com.medical.medical_chekup.restcontroller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medical.medical_chekup.dto.response.ApiResponse;
import com.medical.medical_chekup.model.TToken;
import com.medical.medical_chekup.service.BalanceWithdrawService;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/token")
@RequiredArgsConstructor
public class ApiTokenController {
    private final BalanceWithdrawService balanceWithdrawService;

    @PostMapping("/create/{customerId}")
    public ResponseEntity<ApiResponse<?>> createToken(@PathVariable Long customerId) {
        TToken response = balanceWithdrawService.createToken(customerId);
        ApiResponse<?> apiResponse = new ApiResponse<>(
                "success create token", response, LocalDateTime.now(), HttpStatus.OK.value());
        return ResponseEntity.ok(apiResponse);

    }

    @GetMapping("/{tokenId}")
    public ResponseEntity<ApiResponse<?>> getTokenId(@PathVariable Long tokenId) {
        TToken response = balanceWithdrawService.getToken(tokenId);
        ApiResponse<?> apiResponse = new ApiResponse<>("success get token id", response, LocalDateTime.now(),
                HttpStatus.OK.value());
        return ResponseEntity.ok(apiResponse);
    }

}
