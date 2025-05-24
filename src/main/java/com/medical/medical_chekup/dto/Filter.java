package com.medical.medical_chekup.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Filter {

    private String keyword;
    private String location;
    private String doctorName;
    private String treatment;
}
