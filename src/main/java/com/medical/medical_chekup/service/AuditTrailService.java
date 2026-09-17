package com.medical.medical_chekup.service;

import com.medical.medical_chekup.dto.AuditTrailResDTO;
import com.medical.medical_chekup.dto.response.ApiResponsePagination;

public interface AuditTrailService {

    ApiResponsePagination<AuditTrailResDTO> getAllAuditTrail(String keyword, Integer size, Integer current);

    void record(String action, String entityType, Long entityId, String description);
}