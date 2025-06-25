package com.medical.medical_chekup.restcontroller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.medical.medical_chekup.converter.UserToUserDtoConverter;
import com.medical.medical_chekup.dto.UserDTO;
import com.medical.medical_chekup.dto.response.ApiResponse;
import com.medical.medical_chekup.model.MUser;
// import com.medical.medical_chekup.service.UserService;
import com.medical.medical_chekup.service.impl.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("${api.base.url}/users")
@RequiredArgsConstructor
public class ApiUserController {

    private final UserService userService;
    private final UserToUserDtoConverter userDtoConverter;

    @GetMapping("/")
    public ResponseEntity<ApiResponse<?>> getAll() {

        // System.out.println("=== DEBUG: Authentication: " + authentication);
        // System.out.println("=== DEBUG: Authorities: " +
        // authentication.getAuthorities());
        // System.out.println("=== DEBUG: Masuk ke controller getAll ===");

        // return new String();
        List<MUser> response = this.userService.findAll();
        // System.out.println("=== DEBUG: Jumlah user ditemukan: " + response.size() + "
        // ===");

        List<UserDTO> responseDTO = response.stream().map(this.userDtoConverter::convert)
                .collect(Collectors.toList());
        // System.out.println("=== DEBUG: Response DTO: " + responseDTO + " ===");

        ApiResponse<?> apiResponse = new ApiResponse<>(
                "success get data", responseDTO, LocalDateTime.now(), HttpStatus.OK.value());
        return ResponseEntity.ok(apiResponse);
    }

    // masih 1 password belum confirmation password
    // @PutMapping("setPassword/{email}")
    // public ResponseEntity<ApiResponse<?>> setPassword(@PathVariable String email,
    // @RequestParam String password) {
    // // TODO: process PUT request
    // String passwordSet = userService.createPassword(email, password);
    // ApiResponse<?> apiResponse = new ApiResponse<>(
    // "success set password", passwordSet, LocalDateTime.now(),
    // HttpStatus.OK.value());
    // return ResponseEntity.ok(apiResponse);

    // }
}
