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
public class HospitalDTO {

    private Long hospitalId;
    private String hospitalName;
    private String hospitalCategoryName;
    // private String fullAddress
    private String phone;
    private String fax;
    private String email;
    private String location;
    // private List<LocationDTO> location;

}
