package com.medical.medical_chekup.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medical.medical_chekup.model.MCustomerRelation;

@Repository
public interface CustomerRelationRepository extends JpaRepository<MCustomerRelation, Long> {
}