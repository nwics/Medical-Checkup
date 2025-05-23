package com.medical.medical_chekup.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medical.medical_chekup.model.MMedicalFacilitySchedule;

@Repository
public interface MedicalFacilityScheduleRepository extends JpaRepository<MMedicalFacilitySchedule, Long> {

    MMedicalFacilitySchedule findByMedicalFacilityIdAndIsDeleteIsFalse(Long medicalFacilityId);
}
