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
public class DoctorOfficeDTO {

    private Long id;
    private String doctorName;
    private String specialization;
    private MedicalFacilityDTO medicalFacility;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String serviceUnit;
}
