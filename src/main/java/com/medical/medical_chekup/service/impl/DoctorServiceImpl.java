package com.medical.medical_chekup.service.impl;

// import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
// import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

// import com.medical.medical_chekup.dao.CurrentDoctorSpecializationRepository;
import com.medical.medical_chekup.dao.DoctorOfficeRepository;
import com.medical.medical_chekup.dao.MedicalFacilityScheduleRepository;
import com.medical.medical_chekup.dao.specs.DoctorItemSpecs;
import com.medical.medical_chekup.dto.DoctorListItemDTO;
import com.medical.medical_chekup.dto.Filter;
import com.medical.medical_chekup.dto.Pagination;
import com.medical.medical_chekup.dto.response.ApiResponsePagination;
// import com.medical.medical_chekup.model.MDoctor;
import com.medical.medical_chekup.model.MMedicalFacility;
// import com.medical.medical_chekup.model.MMedicalFacilitySchedule;
// import com.medical.medical_chekup.model.MSpecialization;
// import com.medical.medical_chekup.model.TCurrentDoctorSpecialization;
import com.medical.medical_chekup.model.TDoctorOffice;
import com.medical.medical_chekup.service.DoctorService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

        private final DoctorOfficeRepository doctorOfficeRepository;
        private final MedicalFacilityScheduleRepository medicalFacilityScheduleRepository;

        private String getHospitalName(MMedicalFacility medicalFacility) {
                return medicalFacility != null ? medicalFacility.getName() : null;
        }

        private DoctorListItemDTO mapToDTO(TDoctorOffice tDoctorOffice) {
                DoctorListItemDTO doctorListItemDTO = new DoctorListItemDTO();
                doctorListItemDTO.setDoctorId(tDoctorOffice.getDoctor().getId());
                doctorListItemDTO.setDoctorName(tDoctorOffice.getDoctor().getBiodata().getFullName());
                doctorListItemDTO.setLocationName(tDoctorOffice.getMedicalFacility().getFullAddress());

                List<String> hospitalname = new ArrayList<>();
                if (tDoctorOffice != null && tDoctorOffice.getMedicalFacility() != null) {
                        String hospitalName = getHospitalName(tDoctorOffice.getMedicalFacility());
                        if (hospitalName != null) {
                                hospitalname.add(hospitalName);
                        }
                }
                doctorListItemDTO.setHospitalName(hospitalname);

                String specialization = tDoctorOffice != null ? tDoctorOffice.getSpecialization() : null;
                Integer yearOfExperience = (tDoctorOffice != null && tDoctorOffice.getStartDate() != null)
                                ? (int) ChronoUnit.YEARS.between(tDoctorOffice.getStartDate(), LocalDateTime.now())
                                : null;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                LocalDateTime now = LocalDateTime.now();
                String availibility = (!medicalFacilityScheduleRepository
                                .findByMedicalFacilityIdAndIsDeleteIsFalse(tDoctorOffice.getMedicalFacility().getId())
                                .isEmpty() && now
                                                .isAfter(LocalDateTime.of(now.toLocalDate(),
                                                                LocalTime.parse(medicalFacilityScheduleRepository
                                                                                .findByMedicalFacilityIdAndIsDeleteIsFalse(
                                                                                                tDoctorOffice.getMedicalFacility()
                                                                                                                .getId())
                                                                                .get(0)
                                                                                .getTimeScheduleStart(),
                                                                                formatter)))
                                && now
                                                .isBefore(LocalDateTime.of(now.toLocalDate(),
                                                                LocalTime.parse(medicalFacilityScheduleRepository
                                                                                .findByMedicalFacilityIdAndIsDeleteIsFalse(
                                                                                                tDoctorOffice.getMedicalFacility()
                                                                                                                .getId())
                                                                                .get(0)
                                                                                .getTimeScheduleEnd(), formatter))))
                                                                                                ? "chat"
                                                                                                : "offline";
                doctorListItemDTO.setAvailibility(availibility);
                doctorListItemDTO.setSpecialization(specialization);
                doctorListItemDTO.setYearsOfExperience(yearOfExperience);

                return doctorListItemDTO;

        }

        @Override
        public ApiResponsePagination<DoctorListItemDTO> getAllDoctor(String location, String doctorName, String keyword,
                        String treatment,
                        Integer current, Integer size) {

                Specification<TDoctorOffice> specs = DoctorItemSpecs.searchSpecification(keyword, location, doctorName,
                                treatment);

                // get all doctor office
                Pageable pageable = PageRequest.of(current - 1, size);
                Page<TDoctorOffice> pageResult = doctorOfficeRepository.findAll(specs, pageable);
                List<DoctorListItemDTO> doctorOffices = pageResult.getContent().stream().map(this::mapToDTO)
                                .collect(Collectors.toList());

                // build response
                Pagination pagination = new Pagination();
                pagination.setCurrent(current);
                pagination.setSize(size);
                pagination.setTotal(pageResult.getTotalElements());
                pagination.setTotalPages(pageResult.getTotalPages());
                pagination.setFilter(
                                Filter.builder().location(location)
                                                .doctorName(doctorName)
                                                .keyword(keyword)
                                                .treatment(treatment).build());

                ApiResponsePagination<DoctorListItemDTO> response = new ApiResponsePagination<>();
                response.setMessage("success gett all data");
                response.setData(doctorOffices);
                response.setTimestamp(LocalDateTime.now());
                response.setStatuscode(200);
                response.setPagination(pagination);

                return response;

        }

}
