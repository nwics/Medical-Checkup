package com.medical.medical_chekup.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medical.medical_chekup.model.TCustomerChat;

@Repository
public interface CustomerChatRepository extends JpaRepository<TCustomerChat, Long> {
    Long countByCustomerIdAndIsDeleteIsFalse(Long customerId);
}
