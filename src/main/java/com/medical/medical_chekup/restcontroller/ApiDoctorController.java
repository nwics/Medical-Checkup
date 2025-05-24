package com.medical.medical_chekup.restcontroller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medical.medical_chekup.dto.response.ApiResponsePagination;
import com.medical.medical_chekup.service.DoctorService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/doctor")
@RequiredArgsConstructor
public class ApiDoctorController {

    private final DoctorService doctorService;

    @GetMapping("/")
    public ResponseEntity<ApiResponsePagination<?>> getAllData(@RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "1") Integer current, @RequestParam(defaultValue = "") String location,
            @RequestParam(defaultValue = "") String doctorName, @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "") String treatment) {

        ApiResponsePagination<?> response = doctorService.getAllDoctor(location, doctorName, keyword, treatment,
                current, size);
        return ResponseEntity.ok(response);

    }
}
