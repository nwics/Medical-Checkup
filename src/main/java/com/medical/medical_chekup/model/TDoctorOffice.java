package com.medical.medical_chekup.model;

import java.time.LocalDateTime;

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

@Table(name = "t_doctor_office")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TDoctorOffice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    private MDoctor doctor;

    @ManyToOne
    @JoinColumn(name = "medical_facility_id", referencedColumnName = "id", insertable = false, updatable = false)
    private MMedicalFacility medicalFacility;

    @Column(name = "specialization", length = 100, nullable = false)
    private String specialization;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "service_unit_id", referencedColumnName = "id", insertable = false, updatable = false)
    private MServiceUnit mServiceUnit;
}
