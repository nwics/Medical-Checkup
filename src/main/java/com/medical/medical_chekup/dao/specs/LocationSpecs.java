package com.medical.medical_chekup.dao.specs;

import org.springframework.data.jpa.domain.Specification;

import com.medical.medical_chekup.model.MLocation;
import com.medical.medical_chekup.model.MLocationLevel;

import jakarta.persistence.criteria.Join;

public class LocationSpecs {

    public static Specification<MLocation> hasKeywordName(String keyword) {
        return (root, query, criteriaBuilder) -> {
            Join<MLocation, MLocationLevel> locationJoinLocationLevel = root.join("mLocationLevelId");
            return criteriaBuilder.like(criteriaBuilder.lower(locationJoinLocationLevel.get("name")),
                    "%" + keyword.toLowerCase() + "%");
        };
    }

    public static Specification<MLocation> searchSpecification(String keyword) {
        Specification<MLocation> specification = Specification.where(null);
        if (keyword != null && !keyword.isEmpty()) {
            specification = specification.and(hasKeywordName(keyword));
        }
        return specification;
    }
}
