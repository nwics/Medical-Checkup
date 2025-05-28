package com.medical.medical_chekup.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medical.medical_chekup.model.MBloodGroup;

@Repository
public interface GolonganDarahRepository extends JpaRepository<MBloodGroup, Long> {

}
