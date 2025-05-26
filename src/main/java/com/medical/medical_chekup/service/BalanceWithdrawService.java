package com.medical.medical_chekup.service;

import java.util.List;

import com.medical.medical_chekup.dto.CustomNominalSaldoDTO;
import com.medical.medical_chekup.dto.DefaultSaldoDTO;
import com.medical.medical_chekup.dto.TokenDTO;
import com.medical.medical_chekup.model.TCustomerCustomNominal;
import com.medical.medical_chekup.model.TCustomerWallet;
import com.medical.medical_chekup.model.TCustomerWalletWithdraw;
import com.medical.medical_chekup.model.TToken;

public interface BalanceWithdrawService {

    List<DefaultSaldoDTO> getAllSaldo();

    TCustomerCustomNominal creatCustomerCustomNominal(CustomNominalSaldoDTO customNominal);

    TokenDTO createToken(Long customerId);

    TokenDTO getToken(Long tokenId);

    TCustomerWalletWithdraw BalanceWithdraw(Long customerId, Long walletId);

    Boolean checkPinCustomer(Long customerId, String pin);
}
