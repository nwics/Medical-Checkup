package com.medical.medical_chekup.service;

import java.util.List;

import com.medical.medical_chekup.dto.LocationLevelResDTO;
import com.medical.medical_chekup.dto.LocationReqDTO;
import com.medical.medical_chekup.dto.LocationResDTO;
import com.medical.medical_chekup.dto.ParentLocationDTO;
import com.medical.medical_chekup.dto.response.ApiResponsePagination;
import com.medical.medical_chekup.model.MLocation;

public interface LocationService {

    ApiResponsePagination<LocationResDTO> getAllLocation(String keyword, Integer size, Integer current);

    LocationResDTO createLocation(LocationReqDTO locationReqDTO);

    List<LocationLevelResDTO> getListLocationLevel();

    List<ParentLocationDTO> getListParentLocation();

    void deleteLocation(Long locationId);
}
