package com.medical.medical_chekup.service;

import java.util.List;

import com.medical.medical_chekup.dto.ArrivalHistoryDTO;
import com.medical.medical_chekup.dto.MedicalItemPurchaseDTO;

public interface ArrivalHistoryService {

    List<ArrivalHistoryDTO> getAllArrivalHistory();

    // for generate pdf
    byte[] generateMedicalItemPdf(Long appoinmentId);
}
