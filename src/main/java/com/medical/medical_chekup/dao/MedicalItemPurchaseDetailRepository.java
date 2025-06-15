package com.medical.medical_chekup.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medical.medical_chekup.model.TMedicalItemPurchaseDetail;

@Repository
public interface MedicalItemPurchaseDetailRepository extends JpaRepository<TMedicalItemPurchaseDetail, Long> {

    // List<TMedicalItemPurchaseDetail> findByMedicalItemPurchaseId(Long
    // medicalItemPurchaseId);

    TMedicalItemPurchaseDetail findByMedicalItemPurchaseId(Long medicalItemPurchaseId);

    TMedicalItemPurchaseDetail findByMedicalItemId(Long medicalItemId);
}
