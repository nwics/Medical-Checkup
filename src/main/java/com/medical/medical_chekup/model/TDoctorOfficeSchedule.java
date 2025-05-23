package com.medical.medical_chekup.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "t_doctor_office_schedule")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TDoctorOfficeSchedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "id", insertable = false, updatable = false)
    private MDoctor doctorId;

    @ManyToOne
    @JoinColumn(name = "medical_facility_id", referencedColumnName = "id", insertable = false, updatable = false)
    private MMedicalFacility medicalFacility;

    @Column(name = "slot")
    private Integer slot;
}
