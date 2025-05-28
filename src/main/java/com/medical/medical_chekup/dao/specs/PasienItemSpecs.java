package com.medical.medical_chekup.dao.specs;

import org.springframework.data.jpa.domain.Specification;

import com.medical.medical_chekup.model.MBiodata;
import com.medical.medical_chekup.model.MCustomer;

import jakarta.persistence.criteria.Join;

public class PasienItemSpecs {

    public static Specification<MCustomer> hasKeywordName(String keyword) {
        return (root, query, criteriaBuilder) -> {
            Join<MCustomer, MBiodata> customerBiodataJoin = root.join("mBiodata");
            return criteriaBuilder.like(criteriaBuilder.lower(customerBiodataJoin.get("fullname")),
                    "%" + keyword.toLowerCase() + "%");
        };
    }

    public static Specification<MCustomer> searchSpecification(String keyword) {
        Specification<MCustomer> specification = Specification.where(null);

        if (keyword != null && !keyword.isEmpty()) {
            specification = specification.and(hasKeywordName(keyword));
        }
        return specification;
    }
}
