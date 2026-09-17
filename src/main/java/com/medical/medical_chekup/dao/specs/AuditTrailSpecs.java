package com.medical.medical_chekup.dao.specs;

import org.springframework.data.jpa.domain.Specification;

import com.medical.medical_chekup.model.TAuditTrail;

import jakarta.persistence.criteria.Predicate;

public class AuditTrailSpecs {

    public static Specification<TAuditTrail> hasKeyword(String keyword) {
        return (root, query, criteriaBuilder) -> {
            String likePattern = "%" + keyword.toLowerCase() + "%";
            Predicate actionPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("action")), likePattern);
            Predicate entityPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("entityType")), likePattern);
            Predicate actorPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("actor")), likePattern);
            Predicate descriptionPredicate = criteriaBuilder
                    .like(criteriaBuilder.lower(root.get("description")), likePattern);
            return criteriaBuilder.or(actionPredicate, entityPredicate, actorPredicate, descriptionPredicate);
        };
    }

    public static Specification<TAuditTrail> searchSpecification(String keyword) {
        Specification<TAuditTrail> specification = Specification.where(null);
        if (keyword != null && !keyword.isEmpty()) {
            specification = specification.and(hasKeyword(keyword));
        }
        return specification;
    }
}