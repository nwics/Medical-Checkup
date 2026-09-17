package com.medical.medical_chekup.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import com.medical.medical_chekup.dao.AuditTrailRepository;
import com.medical.medical_chekup.dao.UserRepository;
import com.medical.medical_chekup.dao.specs.AuditTrailSpecs;
import com.medical.medical_chekup.dto.AuditTrailResDTO;
import com.medical.medical_chekup.dto.Filter;
import com.medical.medical_chekup.dto.MyUserPrincipal;
import com.medical.medical_chekup.dto.Pagination;
import com.medical.medical_chekup.dto.response.ApiResponsePagination;
import com.medical.medical_chekup.model.MUser;
import com.medical.medical_chekup.model.TAuditTrail;
import com.medical.medical_chekup.service.AuditTrailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuditTrailServiceImpl implements AuditTrailService {

    private final AuditTrailRepository auditTrailRepository;
    private final UserRepository userRepository;

    private AuditTrailResDTO mapToDTO(TAuditTrail auditTrail) {
        AuditTrailResDTO auditTrailResDTO = new AuditTrailResDTO();
        auditTrailResDTO.setId(auditTrail.getId());
        auditTrailResDTO.setAction(auditTrail.getAction());
        auditTrailResDTO.setEntityType(auditTrail.getEntityType());
        auditTrailResDTO.setEntityId(auditTrail.getEntityId());
        auditTrailResDTO.setDescription(auditTrail.getDescription());
        auditTrailResDTO.setActor(auditTrail.getActor());
        auditTrailResDTO.setCreatedBy(auditTrail.getCreatedBy());
        auditTrailResDTO.setCreatedOn(auditTrail.getCreatedOn());
        return auditTrailResDTO;
    }

    @Override
    public ApiResponsePagination<AuditTrailResDTO> getAllAuditTrail(String keyword, Integer size, Integer current) {
        try {
            Specification<TAuditTrail> specs = AuditTrailSpecs.searchSpecification(keyword);
            Pageable pageable = PageRequest.of(current - 1, size);
            Page<TAuditTrail> auditTrails = auditTrailRepository.findAll(specs, pageable);
            List<AuditTrailResDTO> listAuditTrail = auditTrails.getContent().stream().map(this::mapToDTO)
                    .collect(Collectors.toList());

            Pagination pagination = new Pagination();
            pagination.setCurrent(current);
            pagination.setSize(size);
            pagination.setTotal(auditTrails.getTotalElements());
            pagination.setTotalPages(auditTrails.getTotalPages());
            pagination.setFilter(Filter.builder().keyword(keyword).build());

            ApiResponsePagination<AuditTrailResDTO> apiResponsePagination = new ApiResponsePagination<>();
            apiResponsePagination.setMessage("success get audit trail data");
            apiResponsePagination.setData(listAuditTrail);
            apiResponsePagination.setStatuscode(200);
            apiResponsePagination.setTimestamp(LocalDateTime.now());
            apiResponsePagination.setPagination(pagination);
            return apiResponsePagination;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void record(String action, String entityType, Long entityId, String description) {
        try {
            MUser currentUser = resolveCurrentUser();
            TAuditTrail auditTrail = new TAuditTrail();
            auditTrail.setAction(action);
            auditTrail.setEntityType(entityType);
            auditTrail.setEntityId(entityId);
            auditTrail.setDescription(description);
            auditTrail.setActor(currentUser != null ? currentUser.getEmail() : "system");
            auditTrail.setCreatedBy(currentUser != null ? currentUser.getId() : 1L);
            auditTrail.setCreatedOn(LocalDateTime.now());
            auditTrail.setIsDelete(false);
            auditTrailRepository.save(auditTrail);
        } catch (Exception ignored) {
            // Audit recording must never break the main operation
        }
    }

    private MUser resolveCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof MyUserPrincipal myUserPrincipal) {
            return myUserPrincipal.getMUser();
        }
        if (principal instanceof Jwt jwt) {
            String email = jwt.getSubject();
            if (email == null) {
                return null;
            }
            return userRepository.findByEmailAndIsDeleteIsFalse(email).orElse(null);
        }
        return null;
    }
}