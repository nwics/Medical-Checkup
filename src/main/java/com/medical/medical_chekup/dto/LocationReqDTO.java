package com.medical.medical_chekup.dto;

import com.medical.medical_chekup.model.MLocation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LocationReqDTO {

    private String locationName;
    private Long locationLevelId;
    private Long parentId;

}
