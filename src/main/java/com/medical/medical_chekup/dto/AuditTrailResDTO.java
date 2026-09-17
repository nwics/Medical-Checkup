package com.medical.medical_chekup.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuditTrailResDTO {

    private Long id;
    private String action;
    private String entityType;
    private Long entityId;
    private String description;
    private String actor;
    private Long createdBy;
    private LocalDateTime createdOn;
}