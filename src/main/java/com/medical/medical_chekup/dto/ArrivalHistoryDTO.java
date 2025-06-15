package com.medical.medical_chekup.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.medical.medical_chekup.model.TAppointment;
import com.medical.medical_chekup.model.TAppointmentDone;
import com.medical.medical_chekup.model.TDoctorOffice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArrivalHistoryDTO {

    private Long id;
    private PasienCustomerResDTO pasienCustomerResDTO;

    private String diagnosis;
    private LocalDate appointmentDate;
    // private TAppointment tAppointment;
    // private TAppointmentDone tAppointmentDone;
    // private
    private List<MedicalItemPurchaseDTO> medicalItemPurchaseDTOs;;
    private DoctorOfficeDTO doctorOffice;
}
