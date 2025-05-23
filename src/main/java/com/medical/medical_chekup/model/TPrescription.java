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

@Entity
@Table(name = "t_prescription")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TPrescription extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private TAppointment appointmentId;

    @ManyToOne
    @JoinColumn(name = "medical_item_id")
    private MMedicalItem medicalItemId;

    @Column(name = "dossage")
    private String dossage;

    @Column(name = "directions")
    private String directions;

    @Column(name = "time", length = 100)
    private String time;

    @Column(name = "notes")
    private String notes;

    @Column(name = "printed_on")
    private LocalDateTime printedOn;

    @Column(name = "print_attempt")
    private Integer printAttempt;
}
