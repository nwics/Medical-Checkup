package com.medical.medical_chekup.security;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// import java.util.logging.Logger;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medical.medical_chekup.dto.response.ApiResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("${api.base.url}/user")
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ApiResponse<?> getLoginInfo(Authentication authentication) {
        // TODO: process POST request
        LOGGER.debug("Authenticated user : {}", authentication.getName());

        return new ApiResponse<>("success login", this.authService.createLoginInfo(authentication), LocalDateTime.now(),
                HttpStatus.OK.value());
        // return entity;
    }

}
