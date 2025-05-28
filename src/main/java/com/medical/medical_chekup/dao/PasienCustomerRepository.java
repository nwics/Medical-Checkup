package com.medical.medical_chekup.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.medical.medical_chekup.model.MCustomer;

@Repository
public interface PasienCustomerRepository extends JpaRepository<MCustomer, Long>, JpaSpecificationExecutor<MCustomer> {

}
