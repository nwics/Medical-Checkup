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

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "m_doctor_education")
public class MDoctorEducation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false, referencedColumnName = "id", insertable = false, updatable = false)
    private MDoctor mDoctor;

    @ManyToOne
    @JoinColumn(name = "education_level_id", nullable = false, referencedColumnName = "id", insertable = false, updatable = false)
    private MEducationLevel mEducationLevel;

    @Column(name = "institution_name", nullable = true, length = 100)
    private String institutionName;

    @Column(name = "major", nullable = true, length = 100)
    private String major;

    @Column(name = "start_year", nullable = true, length = 4)
    private String start_year;

    @Column(name = "end_year", nullable = true, length = 4)
    private String end_year;

    @Column(name = "is_last_education", nullable = true)
    private Boolean isLastEducation = false;
}
