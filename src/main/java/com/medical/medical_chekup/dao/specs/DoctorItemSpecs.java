package com.medical.medical_chekup.dao.specs;

import org.springframework.data.jpa.domain.Specification;

import com.medical.medical_chekup.model.MBiodata;
import com.medical.medical_chekup.model.MDoctor;
import com.medical.medical_chekup.model.MLocation;
import com.medical.medical_chekup.model.MMedicalFacility;
import com.medical.medical_chekup.model.TDoctorOffice;
import com.medical.medical_chekup.model.TDoctorTreatment;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

public class DoctorItemSpecs {

    public static Specification<TDoctorOffice> hasKeywordName(String keyword) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("specialization")),
                    "%" + keyword.toLowerCase() + "%");
        };
    }

    public static Specification<TDoctorOffice> hasLocationName(String locationName) {
        return (root, query, criteriaBuilder) -> {
            Join<TDoctorOffice, MMedicalFacility> medicalfacilityJoin = root.join("medicalFacility");
            Join<MMedicalFacility, MLocation> locationJoin = medicalfacilityJoin.join("mLocation");
            return criteriaBuilder.like(criteriaBuilder.lower(locationJoin.get("name")),
                    "%" + locationName.toLowerCase() + "%");
        };
    }

    public static Specification<TDoctorOffice> hasDoctorName(String doctorName) {
        return (root, query, criteriaBuilder) -> {

            Join<TDoctorOffice, MDoctor> doctorJoin = root.join("doctor");
            Join<MDoctor, MBiodata> biodataJoin = doctorJoin.join("biodata");

            return criteriaBuilder.like(criteriaBuilder.lower(biodataJoin.get("fullname")),
                    "%" + doctorName.toLowerCase() + "%");
        };
    }

    public static Specification<TDoctorOffice> hasTreatmentName(String treatmentName) {
        return (root, query, criteriaBuilder) -> {

            Subquery<Long> subquery = query.subquery(Long.class);
            Root<TDoctorTreatment> treatmentRoot = subquery.from(TDoctorTreatment.class);

            subquery.select(treatmentRoot.get("doctor").get("id")).where(criteriaBuilder.equal(
                    treatmentRoot.get("doctor").get("id"), root.get("doctor").get("id")),
                    criteriaBuilder.like(criteriaBuilder.lower(treatmentRoot.get("name")),
                            "%" + treatmentName.toLowerCase() + "%"));
            return criteriaBuilder.exists(subquery);
        };
    }

    public static Specification<TDoctorOffice> searchSpecification(String keyword, String locationName,
            String doctorName, String treatmentName) {
        Specification<TDoctorOffice> specification = Specification.where(null);

        specification = specification
                .and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isDelete"), false));

        if (keyword != null && !keyword.isEmpty()) {
            specification = specification.and(hasKeywordName(keyword));
        }
        if (locationName != null && !locationName.isEmpty()) {
            specification = specification.and(hasLocationName(locationName));
        }
        if (doctorName != null && !doctorName.isEmpty()) {
            specification = specification.and(hasDoctorName(doctorName));
        }
        if (treatmentName != null && !treatmentName.isEmpty()) {
            specification = specification.and(hasTreatmentName(treatmentName));
        }
        return specification;
    }

}
