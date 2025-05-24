package com.medical.medical_chekup.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medical.medical_chekup.model.MWalletDefaultNominal;

@Repository
public interface NominalSaldoRepository extends JpaRepository<MWalletDefaultNominal, Long> {
    List<MWalletDefaultNominal> findAllByIsDeleteIsFalse();
}
