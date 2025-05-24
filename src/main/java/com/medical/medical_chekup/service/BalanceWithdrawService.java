package com.medical.medical_chekup.service;

import java.util.List;

import com.medical.medical_chekup.dto.CustomNominalSaldoDTO;
import com.medical.medical_chekup.dto.DefaultSaldoDTO;
import com.medical.medical_chekup.model.TCustomerCustomNominal;
import com.medical.medical_chekup.model.TToken;

public interface BalanceWithdrawService {

    List<DefaultSaldoDTO> getAllSaldo();

    TCustomerCustomNominal creatCustomerCustomNominal(CustomNominalSaldoDTO customNominal);

    TToken createToken(Long customerId);

    TToken getToken(Long tokenId);
}
