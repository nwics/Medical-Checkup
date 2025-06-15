package com.medical.medical_chekup.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MedicalItemPurchaseDTO {

    private Long id;
    private Long medicalItemId;
    private String medicalItemName;
    private String medicalItemDosage;

}
