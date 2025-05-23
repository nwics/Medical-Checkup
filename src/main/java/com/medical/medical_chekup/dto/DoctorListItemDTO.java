package com.medical.medical_chekup.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorListItemDTO {

    private Long doctorId;
    private String doctorName;
    private String specialization;
    private Integer yearsOfExperience;
    private String hospitalName;
    private String availibility;
}
