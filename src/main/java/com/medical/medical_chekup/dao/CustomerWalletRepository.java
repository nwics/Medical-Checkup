package com.medical.medical_chekup.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medical.medical_chekup.model.TCustomerWallet;

@Repository
public interface CustomerWalletRepository extends JpaRepository<TCustomerWallet, Long> {

    TCustomerWallet findByCustomerIdAndIsDeleteIsFalse(Long customerId);
}
