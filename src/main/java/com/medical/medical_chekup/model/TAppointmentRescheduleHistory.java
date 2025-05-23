package com.medical.medical_chekup.model;

import java.time.LocalDate;

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

@Table(name = "t_appointment_reschedule_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TAppointmentRescheduleHistory extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "appointment_id", referencedColumnName = "id", insertable = false, updatable = false)
    private TAppointment appointment;

    @ManyToOne
    @JoinColumn(name = "doctor_office_schedule_id", referencedColumnName = "id", insertable = false, updatable = false)
    private TDoctorOfficeSchedule doctorOfficeSchedule;

    @Column(name = "appointment_date")
    private LocalDate appointmentDate;
}
