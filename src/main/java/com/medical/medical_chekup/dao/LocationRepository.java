package com.medical.medical_chekup.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.medical.medical_chekup.model.MLocation;

@Repository
public interface LocationRepository extends JpaRepository<MLocation, Long>, JpaSpecificationExecutor<MLocation> {

    // MLocation findByParentIdAndIsDeleteIsFalse(Long id);
}
