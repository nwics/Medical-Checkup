package com.medical.medical_chekup.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medical.medical_chekup.model.TToken;

@Repository
public interface TokenRepository extends JpaRepository<TToken, Long> {

    TToken findByCustomerIdAndIsDeleteIsFalse(Long customerId);

    List<TToken> findByEmailAndIsExpiredIsFalse(String email);
}
