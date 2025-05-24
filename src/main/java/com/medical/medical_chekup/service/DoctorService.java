package com.medical.medical_chekup.service;

import com.medical.medical_chekup.dto.DoctorListItemDTO;
import com.medical.medical_chekup.dto.response.ApiResponsePagination;

public interface DoctorService {

    ApiResponsePagination<DoctorListItemDTO> getAllDoctor(String location, String doctorName, String keyword,
            String treatment,
            Integer current, Integer size);
}
