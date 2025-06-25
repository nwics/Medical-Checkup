package com.medical.medical_chekup.restcontroller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medical.medical_chekup.dto.TokenDTO;
import com.medical.medical_chekup.dto.response.ApiResponse;
import com.medical.medical_chekup.model.TToken;
import com.medical.medical_chekup.service.BalanceWithdrawService;
import com.medical.medical_chekup.service.TokenService;

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
@RequestMapping("${api.base.url}/token")
@RequiredArgsConstructor
public class ApiTokenController {
    // private final BalanceWithdrawService balanceWithdrawService;
    private final TokenService tokenService;

    // @PostMapping("/create/{customerId}")
    // public ResponseEntity<ApiResponse<?>> createToken(@PathVariable Long
    // customerId) {
    // TokenDTO response = balanceWithdrawService.createToken(customerId);
    // ApiResponse<?> apiResponse = new ApiResponse<>(
    // "success create token", response, LocalDateTime.now(),
    // HttpStatus.OK.value());
    // return ResponseEntity.ok(apiResponse);

    // }

    // @GetMapping("/{tokenId}")
    // public ResponseEntity<ApiResponse<?>> getTokenId(@PathVariable Long tokenId)
    // {
    // TokenDTO response = balanceWithdrawService.getToken(tokenId);
    // ApiResponse<?> apiResponse = new ApiResponse<>("success get token id",
    // response, LocalDateTime.now(),
    // HttpStatus.OK.value());
    // return ResponseEntity.ok(apiResponse);
    // }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<?>> createToken(@RequestParam String email) {
        // TODO: process POST request
        TokenDTO otp = tokenService.createNewOtp(email);
        // tokenService.sendOtpToEmail(email, email)
        ApiResponse<?> apiResponse = new ApiResponse<>("success create otp", otp, LocalDateTime.now(),
                HttpStatus.OK.value());

        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/verify")
    public ResponseEntity<ApiResponse<?>> verifyToken(@RequestParam String email, @RequestParam String token) {
        String verify = tokenService.verifyOtp(email, token);
        ApiResponse<?> apiResponse = new ApiResponse<>(
                "success verify token", verify, LocalDateTime.now(), HttpStatus.OK.value());
        return ResponseEntity.ok(apiResponse);
    }

}
