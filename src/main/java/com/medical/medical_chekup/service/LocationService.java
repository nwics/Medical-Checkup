package com.medical.medical_chekup.service;

import com.medical.medical_chekup.dto.LocationReqDTO;
import com.medical.medical_chekup.dto.LocationResDTO;
import com.medical.medical_chekup.dto.response.ApiResponsePagination;

public interface LocationService {

    ApiResponsePagination<LocationResDTO> getAllLocation(String keyword, Integer size, Integer current);

    LocationResDTO createLocation(LocationReqDTO locationReqDTO);
}
