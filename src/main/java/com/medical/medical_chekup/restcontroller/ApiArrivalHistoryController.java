package com.medical.medical_chekup.restcontroller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medical.medical_chekup.dto.ArrivalHistoryDTO;
import com.medical.medical_chekup.dto.response.ApiResponse;
import com.medical.medical_chekup.service.ArrivalHistoryService;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/arrivalhistory")
public class ApiArrivalHistoryController {
    private final ArrivalHistoryService arrivalHistoryService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<?>> getAllHistory() {

        List<ArrivalHistoryDTO> response = arrivalHistoryService.getAllArrivalHistory();

        ApiResponse<?> apiResponse = new ApiResponse<>(
                "success get data", response, LocalDateTime.now(), HttpStatus.OK.value());

        return ResponseEntity.ok(apiResponse);
    }

}
