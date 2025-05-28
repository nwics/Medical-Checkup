package com.medical.medical_chekup.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PasienCustomerDTO {

    private Long biodataId;
    private String pasienName;
    private String golonganDarah;
    private Long golonganDarahId;
    private String rhesusType;
    private String gender;
    private BigDecimal height;
    private BigDecimal weight;
    private LocalDateTime dob;
    // private String relation;
    private Long relationId;
}
