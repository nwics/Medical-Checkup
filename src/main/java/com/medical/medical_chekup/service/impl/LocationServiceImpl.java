package com.medical.medical_chekup.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
// import org.hibernate.query.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.medical.medical_chekup.dao.LocationRepository;
import com.medical.medical_chekup.dao.LocationLevelRepository;
import com.medical.medical_chekup.dao.specs.LocationSpecs;
import com.medical.medical_chekup.dto.Filter;
import com.medical.medical_chekup.dto.LocationLevelResDTO;
import com.medical.medical_chekup.dto.LocationReqDTO;
import com.medical.medical_chekup.dto.LocationResDTO;
import com.medical.medical_chekup.dto.Pagination;
import com.medical.medical_chekup.dto.ParentLocationDTO;
import com.medical.medical_chekup.dto.response.ApiResponsePagination;
import com.medical.medical_chekup.model.MLocation;
import com.medical.medical_chekup.model.MLocationLevel;
import com.medical.medical_chekup.service.LocationService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final LocationLevelRepository locationLevelRepository;

    private LocationResDTO mapToDTO(MLocation mLocation) {
        LocationResDTO locationResDTO = new LocationResDTO();
        locationResDTO.setLocationId(mLocation.getId());
        locationResDTO.setLocationName(mLocation.getName());
        locationResDTO.setLocationLevel(mapToDTOLevelResDTO(mLocation.getMLocationLevelId()));
        locationResDTO.setParentLocationDTO(mapTOParentLocationDTO(mLocation.getParent()));

        return locationResDTO;
    }

    private final LocationLevelResDTO mapToDTOLevelResDTO(MLocationLevel mLocationLevel) {
        LocationLevelResDTO locationLevelResDTO = new LocationLevelResDTO();
        locationLevelResDTO.setCode(mLocationLevel.getAbbreviation());
        locationLevelResDTO.setName(mLocationLevel.getName());
        locationLevelResDTO.setId(mLocationLevel.getId());
        return locationLevelResDTO;
    }

    private final ParentLocationDTO mapTOParentLocationDTO(MLocation mLocation) {
        ParentLocationDTO parentLocationDTO = new ParentLocationDTO();
        if (mLocation != null) {
            parentLocationDTO.setParentId(mLocation.getId());
            parentLocationDTO.setParentName(mLocation.getName());
        } else {
            parentLocationDTO.setParentId(null);
            parentLocationDTO.setParentName(null);
        }
        return parentLocationDTO;
    }

    // private final MLocation mapToModelLocation(LocationReqDTO locationReqDTO) {
    // MLocation mLocation = new MLocation();
    // mLocation.setName(locationReqDTO.getLocationName());
    // mLocation.setParent(locationReqDTO.getParentId());

    // // Fetch the existing MLocationLevel from database
    // MLocationLevel locationLevel =
    // locationLevelRepository.findById(locationReqDTO.getLocationLevelId().getId())
    // .orElseThrow(() -> new RuntimeException("Location level not found"));
    // mLocation.setMLocationLevelId(locationLevel);

    // return mLocation;
    // }

    @Override
    public ApiResponsePagination<LocationResDTO> getAllLocation(String keyword, Integer size, Integer current) {
        // TODO Auto-generated method stub

        try {

            Specification<MLocation> specs = LocationSpecs.searchSpecification(keyword);

            Pageable pageable = PageRequest.of(current - 1, size);
            Page<MLocation> locationItems = locationRepository.findAll(specs, pageable);
            List<LocationResDTO> listLocationItems = locationItems.stream().map(this::mapToDTO)
                    .collect(Collectors.toList());

            Pagination pagination = new Pagination();
            pagination.setCurrent(current);
            pagination.setSize(size);
            pagination.setTotal(locationItems.getTotalElements());
            pagination.setTotalPages(locationItems.getTotalPages());
            pagination.setFilter(Filter.builder().keyword(keyword).build());

            ApiResponsePagination<LocationResDTO> apiResponsePagination = new ApiResponsePagination<>();
            apiResponsePagination.setMessage("success get all data");
            apiResponsePagination.setData(listLocationItems);
            apiResponsePagination.setStatuscode(200);
            apiResponsePagination.setPagination(pagination);
            apiResponsePagination.setTimestamp(LocalDateTime.now());
            return apiResponsePagination;

        } catch (Exception e) {
            // TODO: handle exception
            throw new RuntimeException(e.getMessage());
        }
        // throw new UnsupportedOperationException("Unimplemented method
        // 'getAllLocation'");
    }

    @Override
    public LocationResDTO createLocation(LocationReqDTO locationReqDTO) {
        try {
            MLocation mLocation = new MLocation();
            mLocation.setCreatedBy(1L);
            mLocation.setCreatedOn(LocalDateTime.now());
            mLocation.setIsDelete(false);
            mLocation.setName(locationReqDTO.getLocationName());

            // Set parent location
            MLocation parentLocation = locationRepository
                    .findById(locationReqDTO.getParentId())
                    .orElseThrow(() -> new RuntimeException(
                            "Parent location with ID " + locationReqDTO.getParentId() + " not found"));
            mLocation.setParent(parentLocation);

            // Set location level
            MLocationLevel locationLevel = locationLevelRepository
                    .findById(locationReqDTO.getLocationLevelId())
                    .orElseThrow(() -> new RuntimeException(
                            "Location level with ID " + locationReqDTO.getLocationLevelId() + " not found"));
            mLocation.setMLocationLevelId(locationLevel);

            MLocation savedLocation = locationRepository.save(mLocation);
            return mapToDTO(savedLocation);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<LocationLevelResDTO> getListLocationLevel() {
        // TODO Auto-generated method stub
        try {
            List<MLocationLevel> foundListLocationLevel = locationLevelRepository.findAll();
            return foundListLocationLevel.stream().map(loc -> {
                LocationLevelResDTO locationLevelResDTO = new LocationLevelResDTO();
                locationLevelResDTO.setCode(loc.getAbbreviation());
                locationLevelResDTO.setId(loc.getId());
                locationLevelResDTO.setName(loc.getName());
                return locationLevelResDTO;
            }).collect(Collectors.toList());

        } catch (Exception e) {
            // TODO: handle exception
            throw new RuntimeException(e.getMessage());
        }
        // throw new UnsupportedOperationException("Unimplemented method
        // 'getListLocationLevel'");
    }

    @Override
    public List<ParentLocationDTO> getListParentLocation() {
        try {
            List<MLocation> foundListMLocation = locationRepository.findAll();
            return foundListMLocation.stream()
                    .filter(loc -> loc.getParent() != null) // Only include locations that have a parent
                    .map(loc -> {
                        ParentLocationDTO parentLocationDTO = new ParentLocationDTO();
                        parentLocationDTO.setParentId(loc.getParent().getId());
                        parentLocationDTO.setParentName(loc.getParent().getName());
                        return parentLocationDTO;
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void deleteLocation(Long locationId) {
        // TODO Auto-generated method stub
        try {
            MLocation foundLocation = locationRepository.findById(locationId).orElse(null);
            boolean hasChild = locationRepository.existsByParentIdAndIsDeleteIsFalse(locationId);
            if (hasChild) {
                throw new RuntimeException("Lokasi " + foundLocation.getName() + " masih digunakan");
            }
            if (foundLocation == null) {
                throw new RuntimeException("location not found");
            }

            foundLocation.setIsDelete(true);
            foundLocation.setDeletedBy(1L);
            foundLocation.setDeletedOn(LocalDateTime.now());

            this.locationRepository.save(foundLocation);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
