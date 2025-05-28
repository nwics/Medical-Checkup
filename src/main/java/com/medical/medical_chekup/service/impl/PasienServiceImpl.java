package com.medical.medical_chekup.service.impl;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.medical.medical_chekup.dao.AppointmentRepository;
import com.medical.medical_chekup.dao.BiodataRepository;
import com.medical.medical_chekup.dao.CustomerChatRepository;
import com.medical.medical_chekup.dao.CustomerMemberRepository;
import com.medical.medical_chekup.dao.CustomerRelationRepository;
import com.medical.medical_chekup.dao.GolonganDarahRepository;
import com.medical.medical_chekup.dao.PasienCustomerRepository;
import com.medical.medical_chekup.dao.specs.PasienItemSpecs;
import com.medical.medical_chekup.dto.Filter;
import com.medical.medical_chekup.dto.Pagination;
import com.medical.medical_chekup.dto.PasienCustomerDTO;
// import com.medical.medical_chekup.dto.PasienCustomerReqDTO;
import com.medical.medical_chekup.dto.PasienCustomerResDTO;
import com.medical.medical_chekup.dto.response.ApiResponsePagination;
import com.medical.medical_chekup.model.MBiodata;
import com.medical.medical_chekup.model.MBloodGroup;
import com.medical.medical_chekup.model.MCustomer;
import com.medical.medical_chekup.model.MCustomerMember;
import com.medical.medical_chekup.model.MCustomerRelation;
import com.medical.medical_chekup.model.TAppointment;
import com.medical.medical_chekup.service.PasienService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PasienServiceImpl implements PasienService {

    private final PasienCustomerRepository pasienCustomerRepository;
    private final CustomerMemberRepository customerMemberRepository;
    private final GolonganDarahRepository golonganDarahRepository;
    private final BiodataRepository biodataRepository;
    private final CustomerRelationRepository customerRelationRepository;
    private final AppointmentRepository appointmentRepository;
    private final CustomerChatRepository customerChatRepository;

    private PasienCustomerDTO mapToDto(MCustomer mCustomer) {
        PasienCustomerDTO pasienCustomerDTO = new PasienCustomerDTO();
        pasienCustomerDTO.setBiodataId(mCustomer.getMBiodata().getId());
        pasienCustomerDTO.setPasienName(mCustomer.getMBiodata().getFullName());
        pasienCustomerDTO.setGolonganDarah(mCustomer.getMBloodGroup().getCode());
        pasienCustomerDTO.setGender(mCustomer.getGender());
        pasienCustomerDTO.setRhesusType(mCustomer.getRhesusType());
        pasienCustomerDTO.setHeight(mCustomer.getHeight());
        pasienCustomerDTO.setWeight(mCustomer.getWeight());
        pasienCustomerDTO.setDob(mCustomer.getDob());
        MCustomerMember mCustomerMember = customerMemberRepository
                .findBymCustomerIdAndIsDeleteIsFalse(mCustomer.getId());
        if (mCustomerMember != null && mCustomerMember.getMCustomerRelation() != null) {
            pasienCustomerDTO.setRelationId(mCustomerMember.getMCustomerRelation().getId());
        }
        pasienCustomerDTO.setGolonganDarahId(mCustomer.getMBloodGroup().getId());
        return pasienCustomerDTO;
    }

    private PasienCustomerResDTO mapToDtoRes(MCustomer mCustomer) {
        PasienCustomerResDTO pasienCustomerResDTO = new PasienCustomerResDTO();
        pasienCustomerResDTO.setBiodataName(mCustomer.getMBiodata().getFullName());
        if (mCustomer.getDob() != null) {
            int years = Period.between(mCustomer.getDob().toLocalDate(), LocalDate.now()).getYears();
            pasienCustomerResDTO.setDob(years);
        }
        Long appointment = appointmentRepository.countByCustomerIdAndIsDeleteIsFalse(mCustomer.getId());
        pasienCustomerResDTO.setAppointment(appointment.intValue());

        MCustomerMember mCustomerMember = customerMemberRepository
                .findBymCustomerIdAndIsDeleteIsFalse(mCustomer.getId());
        if (mCustomerMember != null && mCustomerMember.getMCustomerRelation() != null) {
            pasienCustomerResDTO.setRelation(mCustomerMember.getMCustomerRelation().getName());
        }

        Long chatCustomer = customerChatRepository.countByCustomerIdAndIsDeleteIsFalse(mCustomer.getId());
        pasienCustomerResDTO.setCustomerChat(chatCustomer.intValue());

        return pasienCustomerResDTO;
    }

    @Override
    public MCustomer createCustomer(PasienCustomerDTO pasienCustomerDTO) {
        try {
            // Create and save customer
            MCustomer mCustomer = new MCustomer();
            mCustomer.setCreatedOn(LocalDateTime.now());
            mCustomer.setCreatedBy(1L);
            mCustomer.setDob(pasienCustomerDTO.getDob());
            mCustomer.setGender(pasienCustomerDTO.getGender());
            mCustomer.setHeight(pasienCustomerDTO.getHeight());
            mCustomer.setRhesusType(pasienCustomerDTO.getRhesusType());
            mCustomer.setWeight(pasienCustomerDTO.getWeight());
            mCustomer.setIsDelete(false);

            // Set biodata
            MBiodata mBiodata = biodataRepository.findById(pasienCustomerDTO.getBiodataId()).orElse(null);
            mBiodata.setId(pasienCustomerDTO.getBiodataId());
            mBiodata.setCreatedOn(LocalDateTime.now());
            mBiodata.setCreatedBy(1L);
            mCustomer.setMBiodata(mBiodata);

            // Set blood group
            MBloodGroup mBloodGroup = golonganDarahRepository.findById(pasienCustomerDTO.getGolonganDarahId())
                    .orElse(null);
            mBloodGroup.setId(pasienCustomerDTO.getGolonganDarahId());
            mCustomer.setMBloodGroup(mBloodGroup);

            // Save customer first to get the ID
            MCustomer savedCustomer = pasienCustomerRepository.save(mCustomer);

            // Create customer member with relation
            MCustomerMember customerMember = new MCustomerMember();
            customerMember.setMBiodata(mBiodata);
            customerMember.setMCustomer(savedCustomer);

            // Set customer relation
            MCustomerRelation customerRelation = new MCustomerRelation();
            customerRelation.setId(pasienCustomerDTO.getRelationId());
            customerMember.setMCustomerRelation(customerRelation);

            // Set audit fields for customer member
            customerMember.setCreatedOn(LocalDateTime.now());
            customerMember.setCreatedBy(1L);
            customerMember.setIsDelete(false);

            // Save customer member
            customerMemberRepository.save(customerMember);

            return savedCustomer;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create customer: " + e.getMessage());
        }
    }

    @Override
    public ApiResponsePagination<PasienCustomerResDTO> getAllCustomer(String keyword, Integer current, Integer size) {
        try {
            Specification<MCustomer> specs = PasienItemSpecs.searchSpecification(keyword);

            // get all data
            Pageable pageable = PageRequest.of(current - 1, size);
            Page<MCustomer> customerItem = pasienCustomerRepository.findAll(specs, pageable);
            List<PasienCustomerResDTO> listPasienCustomer = customerItem.stream().map(this::mapToDtoRes)
                    .collect(Collectors.toList());

            Pagination pagination = new Pagination();
            pagination.setCurrent(current);
            pagination.setSize(size);
            pagination.setTotal(customerItem.getTotalElements());
            pagination.setTotalPages(customerItem.getTotalPages());
            pagination.setFilter(
                    Filter.builder().keyword(keyword).build());

            ApiResponsePagination<PasienCustomerResDTO> apiResponsePagination = new ApiResponsePagination<>();
            apiResponsePagination.setMessage("success get customer data");
            apiResponsePagination.setStatuscode(200);
            apiResponsePagination.setTimestamp(LocalDateTime.now());
            apiResponsePagination.setPagination(pagination);
            apiResponsePagination.setData(listPasienCustomer);
            return apiResponsePagination;
        } catch (Exception e) {
            // TODO: handle exception
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public MCustomer editCustomer(Long customerId, PasienCustomerDTO pasienCustomerDTO) {
        // TODO Auto-generated method stub
        try {
            MCustomer foundCustomer = pasienCustomerRepository.findById(customerId).orElse(null);
            if (foundCustomer == null) {
                throw new RuntimeException("customer not found");
            }
            MCustomer customer = foundCustomer;
            customer.setModifiedBy(1L);
            customer.setModifiedOn(LocalDateTime.now());
            customer.setDob(pasienCustomerDTO.getDob());
            customer.setGender(pasienCustomerDTO.getGender());
            customer.setRhesusType(pasienCustomerDTO.getRhesusType());
            customer.setWeight(pasienCustomerDTO.getWeight());
            customer.setHeight(pasienCustomerDTO.getHeight());

            MBiodata foundBiodata = biodataRepository.findById(pasienCustomerDTO.getBiodataId()).orElse(null);
            if (foundBiodata == null) {
                throw new RuntimeException("biodata not found");
            }
            MBiodata biodata = foundBiodata;
            if (pasienCustomerDTO.getPasienName() != null) {
                foundBiodata.setFullName(pasienCustomerDTO.getPasienName());
            }
            biodata.setModifiedOn(LocalDateTime.now());
            biodata.setModifiedBy(1L);
            biodataRepository.save(biodata);
            customer.setMBiodata(biodata);

            MBloodGroup foundBloodGroup = golonganDarahRepository.findById(pasienCustomerDTO.getGolonganDarahId())
                    .orElse(null);
            if (foundBloodGroup == null) {
                throw new RuntimeException("blood group not found");
            }
            if (pasienCustomerDTO.getGolonganDarah() != null) {
                foundBloodGroup.setCode(pasienCustomerDTO.getGolonganDarah());
            }
            foundBloodGroup.setModifiedBy(1L);
            foundBloodGroup.setModifiedOn(LocalDateTime.now());
            golonganDarahRepository.save(foundBloodGroup);

            customer.setMBloodGroup(foundBloodGroup);

            this.pasienCustomerRepository.save(customer);
            return customer;
        } catch (Exception e) {
            // TODO: handle exception
            throw new RuntimeException(e.getMessage());
        }
        // throw new UnsupportedOperationException("Unimplemented method
        // 'editCustomer'");
    }

    @Override
    public void deleteMultipleCustomer(List<Long> customerId) {
        try {
            List<MCustomer> mCustomers = pasienCustomerRepository.findAllById(customerId);
            mCustomers.forEach(customer -> {
                customer.setDeletedBy(1L);
                customer.setIsDelete(true);
                customer.setDeletedOn(LocalDateTime.now());
            });
            pasienCustomerRepository.saveAll(mCustomers);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        // throw new UnsupportedOperationException("Unimplemented method
        // 'deleteMultipleCustomer'");
    }
}
