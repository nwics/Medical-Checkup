package com.medical.medical_chekup.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medical.medical_chekup.model.TMedicalItemPurchase;

@Repository
public interface MedicalItemPurchaseRepository extends JpaRepository<TMedicalItemPurchase, Long> {

    List<TMedicalItemPurchase> findByCustomerId(Long customerId);
}
