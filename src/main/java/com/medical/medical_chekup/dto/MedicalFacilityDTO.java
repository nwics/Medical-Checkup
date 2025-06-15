package com.medical.medical_chekup.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MedicalFacilityDTO {

    private Long id;
    private String facilityName;
    private String address;
    private String phone;
    private String categoryName;
    private String locationName;
}
