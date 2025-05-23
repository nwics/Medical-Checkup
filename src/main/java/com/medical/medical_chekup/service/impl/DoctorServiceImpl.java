package com.medical.medical_chekup.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.medical.medical_chekup.dao.CurrentDoctorSpecializationRepository;
import com.medical.medical_chekup.dao.DoctorOfficeRepository;
import com.medical.medical_chekup.dao.MedicalFacilityScheduleRepository;
import com.medical.medical_chekup.dto.DoctorListItemDTO;
import com.medical.medical_chekup.model.MDoctor;
import com.medical.medical_chekup.model.MMedicalFacilitySchedule;
import com.medical.medical_chekup.model.MSpecialization;
import com.medical.medical_chekup.model.TCurrentDoctorSpecialization;
import com.medical.medical_chekup.model.TDoctorOffice;
import com.medical.medical_chekup.service.DoctorService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final CurrentDoctorSpecializationRepository currentDoctorSpecializationRepository;
    private final DoctorOfficeRepository doctorOfficeRepository;
    private final MedicalFacilityScheduleRepository medicalFacilityScheduleRepository;

    private DoctorListItemDTO mapToDTO(MDoctor doctor) {
        TDoctorOffice tDoctorOffice = doctorOfficeRepository.findByDoctorIdAndIsDeleteIsFalse(doctor.getId());
        MMedicalFacilitySchedule medicalFacilitySchedule = medicalFacilityScheduleRepository
                .findByMedicalFacilityIdAndIsDeleteIsFalse(tDoctorOffice.getMedicalFacility().getId());

        DoctorListItemDTO doctorListItemDTO = new DoctorListItemDTO();
        doctorListItemDTO.setDoctorId(doctor.getId());
        doctorListItemDTO.setDoctorName(doctor.getBiodata().getFullName());
        doctorListItemDTO.setHospitalName(medicalFacilitySchedule.getMedicalFacility().getName());
        String specialization = tDoctorOffice != null ? tDoctorOffice.getSpecialization() : null;
        Integer yearOfExperience = (tDoctorOffice != null && tDoctorOffice.getStartDate() != null)
                ? (int) ChronoUnit.YEARS.between(tDoctorOffice.getStartDate(), LocalDateTime.now())
                : null;
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        String availibility = (LocalDateTime.now()
                .isAfter(LocalDateTime.parse(medicalFacilitySchedule.getTimeScheduleStart(), formatter))
                && LocalDateTime.now()
                        .isBefore(LocalDateTime.parse(medicalFacilitySchedule.getTimeScheduleEnd(), formatter)))
                                ? "chat"
                                : "online";
        doctorListItemDTO.setAvailibility(availibility);
        doctorListItemDTO.setSpecialization(specialization);
        doctorListItemDTO.setYearsOfExperience(yearOfExperience);

        return doctorListItemDTO;

    }

    @Override
    public DoctorListItemDTO getAllFindDoctor(Long doctorId) {
        // TODO Auto-generated method stub
        try {

        } catch (Exception e) {
            // TODO: handle exception
        }
        throw new UnsupportedOperationException("Unimplemented method 'getAllFindDoctor'");
    }

}
