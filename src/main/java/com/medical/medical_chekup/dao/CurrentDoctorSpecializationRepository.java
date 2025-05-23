package com.medical.medical_chekup.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// import com.medical.medical_chekup.model.MSpecialization;
import com.medical.medical_chekup.model.TCurrentDoctorSpecialization;

@Repository
public interface CurrentDoctorSpecializationRepository extends JpaRepository<TCurrentDoctorSpecialization, Long> {

    Optional<TCurrentDoctorSpecialization> findByDoctorIdAndIsDeleteIsFalse(Long doctorId);
}
