package com.medical.medical_chekup.dto;

import java.time.LocalDateTime;

import com.medical.medical_chekup.model.MBiodata;
import com.medical.medical_chekup.model.MRole;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private LocalDateTime lastLogin;
    private String email;
    private MRole role;
    private MBiodata biodata;
}
