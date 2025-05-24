package com.medical.medical_chekup.service.impl;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

import com.medical.medical_chekup.dao.CustomNominalCustomerRepository;
import com.medical.medical_chekup.dao.CustomerRepository;
import com.medical.medical_chekup.dao.NominalSaldoRepository;
import com.medical.medical_chekup.dao.TokenRepository;
import com.medical.medical_chekup.dto.CustomNominalSaldoDTO;
import com.medical.medical_chekup.dto.DefaultSaldoDTO;
import com.medical.medical_chekup.dto.TokenDTO;
import com.medical.medical_chekup.model.MWalletDefaultNominal;
import com.medical.medical_chekup.model.TCustomerCustomNominal;
import com.medical.medical_chekup.model.TToken;
import com.medical.medical_chekup.model.MCustomer;
import com.medical.medical_chekup.service.BalanceWithdrawService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BalanceWithdrawServiceImpl implements BalanceWithdrawService {

    private final NominalSaldoRepository nominalSaldoRepository;
    private final CustomNominalCustomerRepository customNominalCustomerRepository;
    private final TokenRepository tokenRepository;
    private final CustomerRepository customerRepository;

    private DefaultSaldoDTO mapToDTO(MWalletDefaultNominal defaultNominal) {
        DefaultSaldoDTO defaultSaldoDTO = new DefaultSaldoDTO();
        defaultSaldoDTO.setId(defaultNominal.getId());
        defaultSaldoDTO.setSaldo(BigInteger.valueOf(defaultNominal.getNominal()));
        return defaultSaldoDTO;
    }

    private CustomNominalSaldoDTO mapToDTO(TCustomerCustomNominal customerCustomNominal) {
        CustomNominalSaldoDTO customNominalSaldoDTO = new CustomNominalSaldoDTO();
        customNominalSaldoDTO.setId(customerCustomNominal.getId());
        customNominalSaldoDTO.setCustomerId(customerCustomNominal.getCustomer().getId());
        customNominalSaldoDTO.setNominal(BigInteger.valueOf(customerCustomNominal.getNominal()));
        return customNominalSaldoDTO;
    }

    private TokenDTO mapTokenDTO(TToken tToken) {
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setCustomerId(tToken.getCustomer().getId());
        boolean expired = false;
        if (LocalDateTime.now().isAfter(tToken.getExpiredOn())) {
            expired = true;
        }
        tokenDTO.setExpired(expired);
        tokenDTO.setExpiredOn(tToken.getExpiredOn());
        tokenDTO.setToken(tToken.getToken());
        return tokenDTO;

    }

    @Override
    public List<DefaultSaldoDTO> getAllSaldo() {
        List<DefaultSaldoDTO> result = new ArrayList<>();
        List<MWalletDefaultNominal> defaultNominals = nominalSaldoRepository.findAllByIsDeleteIsFalse();
        if (defaultNominals != null && !defaultNominals.isEmpty()) {
            result = defaultNominals.stream()
                    .map(nominal -> {
                        DefaultSaldoDTO dto = new DefaultSaldoDTO();
                        dto.setId(nominal.getId());
                        dto.setSaldo(BigInteger.valueOf(nominal.getNominal()));
                        dto.setType("default");
                        return dto;
                    })
                    .collect(Collectors.toList());
        }

        List<TCustomerCustomNominal> customNominals = customNominalCustomerRepository.findAllByIsDeleteIsFalse();
        if (customNominals != null && !customNominals.isEmpty()) {
            List<DefaultSaldoDTO> customDefaultNominals = customNominals.stream()
                    .map(custom -> {
                        DefaultSaldoDTO dto = new DefaultSaldoDTO();
                        dto.setId(custom.getId());
                        dto.setSaldo(BigInteger.valueOf(custom.getNominal()));
                        dto.setType("custom");
                        return dto;
                    })
                    .collect(Collectors.toList());
            result.addAll(customDefaultNominals);
        }
        return result;
    }

    @Override
    public TCustomerCustomNominal creatCustomerCustomNominal(CustomNominalSaldoDTO customNominal) {
        try {
            TCustomerCustomNominal newCustomNominal = new TCustomerCustomNominal();
            newCustomNominal.setNominal(customNominal.getNominal().intValue());

            MCustomer customer = new MCustomer();
            customer.setId(customNominal.getCustomerId());
            newCustomNominal.setCustomer(customer);

            newCustomNominal.setCreatedOn(LocalDateTime.now());
            newCustomNominal.setIsDelete(false);
            newCustomNominal.setCreatedBy(1L);

            TCustomerCustomNominal saved = customNominalCustomerRepository.save(newCustomNominal);
            return saved;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create custom nominal: " + e.getMessage());
        }
    }

    @Override
    public TokenDTO createToken(Long customerId) {
        try {
            MCustomer foundCustomer = customerRepository.findById(customerId).orElse(null);

            if (foundCustomer == null) {
                throw new RuntimeException("Customer not found");
            }

            TToken newTToken = new TToken();
            newTToken.setCreatedBy(1L);
            newTToken.setCreatedOn(LocalDateTime.now());
            newTToken.setIsDelete(false);

            MCustomer customer = new MCustomer();
            customer.setId(customerId);
            newTToken.setCustomer(foundCustomer);

            newTToken.setExpired(false);
            Random random = new Random();
            int randomNum = 100000 + random.nextInt(900000);
            newTToken.setToken(String.valueOf(randomNum));
            newTToken.setExpiredOn(LocalDateTime.now().plusHours(1));

            tokenRepository.save(newTToken);

            return mapTokenDTO(newTToken);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create token: " + e.getMessage());
        }
    }

    @Override
    public TokenDTO getToken(Long tokenId) {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'getToken'");
        TToken token = tokenRepository.findById(tokenId).orElse(null);
        if (!token.isExpired() && LocalDateTime.now().isAfter(token.getExpiredOn())) {
            token.setExpired(true);
            tokenRepository.save(token);
        }
        return mapTokenDTO(token);
    }

}
