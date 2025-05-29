package com.medical.medical_chekup.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ParentLocationDTO {

    private Long parentId;
    private String parentName;
}
