package com.medical.medical_chekup.service;

import com.medical.medical_chekup.dto.DoctorListItemDTO;

public interface DoctorService {

    DoctorListItemDTO getAllFindDoctor(Long doctorId);
}
