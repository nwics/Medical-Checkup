package com.medical.medical_chekup.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PasienCustomerResDTO {

    private String biodataName;
    private String relation;
    private Integer dob;
    private Integer appointment;
    private Integer customerChat;
}
