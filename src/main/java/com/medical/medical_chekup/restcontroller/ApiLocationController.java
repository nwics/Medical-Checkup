package com.medical.medical_chekup.restcontroller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medical.medical_chekup.dto.LocationLevelResDTO;
import com.medical.medical_chekup.dto.LocationReqDTO;
import com.medical.medical_chekup.dto.LocationResDTO;
import com.medical.medical_chekup.dto.ParentLocationDTO;
import com.medical.medical_chekup.dto.response.ApiResponse;
import com.medical.medical_chekup.dto.response.ApiResponsePagination;
import com.medical.medical_chekup.model.MLocation;
import com.medical.medical_chekup.service.LocationService;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/level")
    public ResponseEntity<ApiResponse<?>> getAllLevelLocation() {
        List<LocationLevelResDTO> response = locationService.getListLocationLevel();
        ApiResponse<?> apiResponse = new ApiResponse<>(
                "success get all level", response, LocalDateTime.now(), HttpStatus.OK.value());
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<?>> getAllLocation() {
        List<ParentLocationDTO> response = locationService.getListParentLocation();
        ApiResponse<?> apiResponse = new ApiResponse<>(
                "success get all location", response, LocalDateTime.now(), HttpStatus.OK.value());
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/delete/{locationId}")
    public ResponseEntity<ApiResponse<?>> deleteLocation(@PathVariable Long locationId) {
        locationService.deleteLocation(locationId);
        ApiResponse<?> apiResponse = new ApiResponse<>(
                "success delete location", null, LocalDateTime.now(), HttpStatus.OK.value());
        return ResponseEntity.ok(apiResponse);

    }

}
