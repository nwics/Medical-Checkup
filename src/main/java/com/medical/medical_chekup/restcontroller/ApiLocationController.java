package com.medical.medical_chekup.restcontroller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medical.medical_chekup.dto.LocationReqDTO;
import com.medical.medical_chekup.dto.LocationResDTO;
import com.medical.medical_chekup.dto.response.ApiResponse;
import com.medical.medical_chekup.dto.response.ApiResponsePagination;
import com.medical.medical_chekup.service.LocationService;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequestMapping("/api/location")
@RestController
@RequiredArgsConstructor
public class ApiLocationController {

    private final LocationService locationService;

    @GetMapping("/")
    public ResponseEntity<ApiResponsePagination<?>> getAllData(@RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "1") Integer current) {

        ApiResponsePagination<?> response = locationService.getAllLocation(keyword, size, current);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/create/")
    public ResponseEntity<ApiResponse<?>> createLocation(@RequestBody LocationReqDTO locationReqDTO) {
        LocationResDTO response = locationService.createLocation(locationReqDTO);
        ApiResponse<?> apiResponse = new ApiResponse<>("success create location", response, LocalDateTime.now(),
                HttpStatus.OK.value());

        return ResponseEntity.ok(apiResponse);
    }

}
