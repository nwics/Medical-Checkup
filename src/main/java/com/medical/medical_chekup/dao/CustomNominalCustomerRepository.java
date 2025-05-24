package com.medical.medical_chekup.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medical.medical_chekup.model.TCustomerCustomNominal;

@Repository
public interface CustomNominalCustomerRepository extends JpaRepository<TCustomerCustomNominal, Long> {

    List<TCustomerCustomNominal> findAllByIsDeleteIsFalse();

    TCustomerCustomNominal findByIdAndIsDeleteIsFalse(Long id);
}
