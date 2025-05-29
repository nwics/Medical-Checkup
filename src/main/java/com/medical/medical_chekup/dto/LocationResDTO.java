package com.medical.medical_chekup.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LocationResDTO {

    private Long locationId;
    private String locationName;
    private LocationLevelResDTO locationLevel;
    private ParentLocationDTO parentLocationDTO;
}
