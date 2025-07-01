package com.medical.medical_chekup.service.impl;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.medical.medical_chekup.dao.AppoinmentDoneRepository;
import com.medical.medical_chekup.dao.MedicalItemPurchaseDetailRepository;
import com.medical.medical_chekup.dao.MedicalItemPurchaseRepository;
import com.medical.medical_chekup.dto.ArrivalHistoryDTO;
import com.medical.medical_chekup.dto.DoctorOfficeDTO;
import com.medical.medical_chekup.dto.MedicalFacilityDTO;
import com.medical.medical_chekup.dto.MedicalItemPurchaseDTO;
import com.medical.medical_chekup.dto.PasienCustomerResDTO;
import com.medical.medical_chekup.model.TAppointmentDone;
import com.medical.medical_chekup.model.TMedicalItemPurchase;
import com.medical.medical_chekup.model.TMedicalItemPurchaseDetail;
import com.medical.medical_chekup.service.ArrivalHistoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArrivalHistoryServiceImpl implements ArrivalHistoryService {

        private final MedicalItemPurchaseDetailRepository medicalItemPurchaseDetailRepository;
        private final MedicalItemPurchaseRepository medicalItemPurchaseRepository;
        private final AppoinmentDoneRepository appoinmentDoneRepository;

        private ArrivalHistoryDTO mapToDTO(TAppointmentDone tAppointmentDone) {
                ArrivalHistoryDTO arrivalHistoryDTO = new ArrivalHistoryDTO();
                arrivalHistoryDTO.setId(tAppointmentDone.getId());

                /*
                 * dirapikan lagi pada bagian Arrival History dto
                 */

                // set pasien customer
                PasienCustomerResDTO pasienCustomerResDTO = new PasienCustomerResDTO();
                pasienCustomerResDTO
                                .setBiodataName(tAppointmentDone.getAppointment().getCustomer().getMBiodata()
                                                .getFullName());
                // pasienCustomerResDTO.setRelation(tAppointmentDone.getAppointment().getCustomer());
                // pasienCustomerResDTO.setDob(tAppointmentDone.getAppointment().getCustomer().getDob());
                arrivalHistoryDTO.setPasienCustomerResDTO(pasienCustomerResDTO);

                // set doctor office
                DoctorOfficeDTO doctorOfficeDTO = new DoctorOfficeDTO();
                doctorOfficeDTO.setDoctorName(
                                tAppointmentDone.getAppointment().getDoctorOffice().getDoctor().getBiodata()
                                                .getFullName());
                doctorOfficeDTO.setId(tAppointmentDone.getAppointment().getDoctorOffice().getId());
                doctorOfficeDTO.setSpecialization(
                                tAppointmentDone.getAppointment().getDoctorOffice().getSpecialization());
                doctorOfficeDTO.setStartDate(tAppointmentDone.getAppointment().getDoctorOffice().getStartDate());
                doctorOfficeDTO.setEndDate(tAppointmentDone.getAppointment().getDoctorOffice().getEndDate());
                doctorOfficeDTO.setServiceUnit(
                                tAppointmentDone.getAppointment().getDoctorOffice().getMServiceUnit().getName());

                MedicalFacilityDTO medicalFacilityDTO = new MedicalFacilityDTO();
                medicalFacilityDTO.setId(tAppointmentDone.getAppointment().getDoctorOffice().getId());
                medicalFacilityDTO
                                .setFacilityName(tAppointmentDone.getAppointment().getDoctorOffice()
                                                .getMedicalFacility().getName());
                medicalFacilityDTO
                                .setAddress(tAppointmentDone.getAppointment().getDoctorOffice().getMedicalFacility()
                                                .getFullAddress());
                medicalFacilityDTO
                                .setPhone(tAppointmentDone.getAppointment().getDoctorOffice().getMedicalFacility()
                                                .getPhone());
                medicalFacilityDTO.setCategoryName(
                                tAppointmentDone.getAppointment().getDoctorOffice().getMedicalFacility()
                                                .getMMedicalFacilityCategory().getName());
                medicalFacilityDTO.setLocationName(
                                tAppointmentDone.getAppointment().getDoctorOffice().getMedicalFacility().getMLocation()
                                                .getName());

                doctorOfficeDTO.setMedicalFacility(medicalFacilityDTO);

                arrivalHistoryDTO.setDoctorOffice(doctorOfficeDTO);

                // set appointment date
                arrivalHistoryDTO.setAppointmentDate(tAppointmentDone.getAppointment().getAppointmentDate());
                // set diagnoisis
                arrivalHistoryDTO.setDiagnosis(tAppointmentDone.getDiagnosis());

                // set medical item list purchase
                List<MedicalItemPurchaseDTO> medicalItemPurchaseDTOs = new ArrayList<>();
                for (TMedicalItemPurchase medicalItemPurchase : medicalItemPurchaseRepository
                                .findByCustomerId(tAppointmentDone.getAppointment().getCustomer().getId())) {
                        MedicalItemPurchaseDTO medicalItemPurchaseDTO = new MedicalItemPurchaseDTO();
                        medicalItemPurchaseDTO.setId(medicalItemPurchase.getId());

                        TMedicalItemPurchaseDetail medicalItemPurchaseDetail = medicalItemPurchaseDetailRepository
                                        .findByMedicalItemPurchaseId(medicalItemPurchase.getId());

                        medicalItemPurchaseDTO.setMedicalItemId(medicalItemPurchaseDetail.getMedicalItem().getId());
                        medicalItemPurchaseDTO.setMedicalItemName(medicalItemPurchaseDetail.getMedicalItem().getName());
                        medicalItemPurchaseDTO
                                        .setMedicalItemDosage(medicalItemPurchaseDetail.getMedicalItem().getDosage());

                        medicalItemPurchaseDTOs.add(medicalItemPurchaseDTO);

                }
                arrivalHistoryDTO.setMedicalItemPurchaseDTOs(medicalItemPurchaseDTOs);
                return arrivalHistoryDTO;

        }

        @Override
        public List<ArrivalHistoryDTO> getAllArrivalHistory() {
                // TODO Auto-generated method stub

                List<TAppointmentDone> appointmentDones = appoinmentDoneRepository.findAll();
                return appointmentDones.stream().map(this::mapToDTO).collect(Collectors.toList());
                // throw new UnsupportedOperationException("Unimplemented method
                // 'getAllArrivalHistory'");
        }

        @Override
        public byte[] generateMedicalItemPdf(Long appointmentId) {
                // TODO Auto-generated method stub
                TAppointmentDone foundAppointmentDone = appoinmentDoneRepository.findById(appointmentId)
                                .orElseThrow(() -> new RuntimeException("Appointmen ID not found"));
                ArrivalHistoryDTO dto = mapToDTO(foundAppointmentDone);
                List<MedicalItemPurchaseDTO> items = dto.getMedicalItemPurchaseDTOs();

                ByteArrayOutputStream out = new ByteArrayOutputStream();

                PdfWriter writer = new PdfWriter(out);
                PdfDocument pdf = new PdfDocument(writer);
                Document document = new Document(pdf);

                document.add(new Paragraph("Daftar obat yang dibeli "));
                document.add(new Paragraph(" "));

                // create table : ID, Nama, Dosis
                float[] columWidths = { 50F, 200F, 100F };
                Table table = new Table(columWidths);
                table.addCell("ID");
                table.addCell("Nama Obat");
                table.addCell("Dosis");

                for (MedicalItemPurchaseDTO item : items) {
                        table.addCell(String.valueOf(item.getMedicalItemId()));
                        table.addCell(String.valueOf(item.getMedicalItemName()));
                        table.addCell(String.valueOf(item.getMedicalItemDosage()));
                }

                document.add(table);
                document.close();
                // throw new UnsupportedOperationException("Unimplemented method
                // 'generateMedicalItemPdf'");
                return out.toByteArray();

        }

}
