package com.medical.medical_chekup.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medical.medical_chekup.model.TDoctorOffice;

@Repository
public interface DoctorOfficeRepository extends JpaRepository<TDoctorOffice, Long> {

    TDoctorOffice findByDoctorIdAndIsDeleteIsFalse(Long doctorId);
}
