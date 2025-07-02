package com.medical.medical_chekup.restcontroller;

import org.springframework.web.bind.annotation.RestController;

import com.medical.medical_chekup.dto.TokenDTO;
import com.medical.medical_chekup.dto.response.ApiResponse;
import com.medical.medical_chekup.service.ForgetPasswordService;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.base.url}/forgot")
public class ApiForgetPasswordController {

    private final ForgetPasswordService forgetPasswordService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<?>> createOTP(@RequestParam String email) {
        // TODO: process POST request

        // return entity;
        TokenDTO response = forgetPasswordService.createNewOtp(email);

        ApiResponse<?> apiResponse = new ApiResponse<>("Success create otp", response, LocalDateTime.now(),
                HttpStatus.OK.value());
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/verify")
    public ResponseEntity<ApiResponse<?>> verifyOtp(@RequestParam String email, @RequestParam String otp) {
        // TODO: process POST request

        // return entity;
        String verify = forgetPasswordService.verifyOtp(email, otp);
        ApiResponse<?> apiResponse = new ApiResponse<>("succses verify token", verify, LocalDateTime.now(),
                HttpStatus.OK.value());

        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("setPassword/{email}")
    public ResponseEntity<ApiResponse<?>> setPassword(@RequestParam String setPassword, @RequestParam String password,
            @PathVariable String email) {
        // TODO: process PUT request

        // return entity;
        String response = forgetPasswordService.createPassword(password, setPassword, email);
        ApiResponse<?> apiResponse = new ApiResponse<>("success set password", response, LocalDateTime.now(),
                HttpStatus.OK.value());
        return ResponseEntity.ok(apiResponse);
    }

}
