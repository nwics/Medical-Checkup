package com.medical.medical_chekup.restcontroller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.medical.medical_chekup.dto.response.ApiResponsePagination;
import com.medical.medical_chekup.service.AuditTrailService;

import lombok.RequiredArgsConstructor;

@RequestMapping("${api.base.url}/audittrail")
@RestController
@RequiredArgsConstructor
public class ApiAuditTrailController {

    private final AuditTrailService auditTrailService;

    @GetMapping("/")
    public ResponseEntity<ApiResponsePagination<?>> getAllData(@RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "1") Integer current, @RequestParam(defaultValue = "10") Integer size) {

        ApiResponsePagination<?> response = auditTrailService.getAllAuditTrail(keyword, size, current);

        return ResponseEntity.ok(response);
    }
}