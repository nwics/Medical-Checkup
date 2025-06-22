package com.medical.medical_chekup.restcontroller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.medical.medical_chekup.dto.response.ApiResponse;
import com.medical.medical_chekup.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class ApiUserController {

    private final UserService userService;

    // masih 1 password belum confirmation password
    @PutMapping("setPassword/{email}")
    public ResponseEntity<ApiResponse<?>> setPassword(@PathVariable String email, @RequestParam String password) {
        // TODO: process PUT request
        String passwordSet = userService.createPassword(email, password);
        ApiResponse<?> apiResponse = new ApiResponse<>(
                "success set password", passwordSet, LocalDateTime.now(), HttpStatus.OK.value());
        return ResponseEntity.ok(apiResponse);

    }
}
