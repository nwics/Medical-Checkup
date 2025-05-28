package com.medical.medical_chekup.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medical.medical_chekup.model.MBiodata;

@Repository
public interface BiodataRepository extends JpaRepository<MBiodata, Long> {

}
