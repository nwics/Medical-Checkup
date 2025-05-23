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

@Table(name = "t_doctor_office_treatment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TDoctorOfficeTreatment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doctor_treatment_id", referencedColumnName = "id", insertable = false, updatable = false)
    private TDoctorTreatment doctorTreatment;

    @ManyToOne
    @JoinColumn(name = "doctor_office_id", referencedColumnName = "id", insertable = false, updatable = false)
    private TDoctorOffice doctorOffice;
}
