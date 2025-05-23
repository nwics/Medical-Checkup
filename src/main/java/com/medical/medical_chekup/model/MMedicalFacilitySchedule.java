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
@Table(name = "m_medical_facility_schedule")
public class MMedicalFacilitySchedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "medical_facility_id", referencedColumnName = "id", insertable = false, updatable = false)
    private MMedicalFacility mMedicalFacility;

    @Column(name = "day", nullable = true, length = 10)
    private String day;

    @Column(name = "time_schedule_start", nullable = true, length = 10)
    private String timeScheduleStart;

    @Column(name = "time_Schedule_end", nullable = true, length = 10)
    private String timeScheduleEnd;
}