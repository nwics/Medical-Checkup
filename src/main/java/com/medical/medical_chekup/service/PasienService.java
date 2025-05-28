package com.medical.medical_chekup.service;

import java.util.List;

import com.medical.medical_chekup.dto.PasienCustomerDTO;
// import com.medical.medical_chekup.dto.PasienCustomerReqDTO;
import com.medical.medical_chekup.dto.PasienCustomerResDTO;
import com.medical.medical_chekup.dto.response.ApiResponsePagination;
import com.medical.medical_chekup.model.MCustomer;

public interface PasienService {

    MCustomer createCustomer(PasienCustomerDTO pasienCustomerDTO);

    ApiResponsePagination<PasienCustomerResDTO> getAllCustomer(String keyword, Integer current, Integer size);

    MCustomer editCustomer(Long customerId, PasienCustomerDTO pasienCustomerDTO);

    void deleteMultipleCustomer(List<Long> customerId);
}
