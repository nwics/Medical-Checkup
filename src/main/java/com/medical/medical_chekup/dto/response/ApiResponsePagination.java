package com.medical.medical_chekup.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.medical.medical_chekup.dto.Pagination;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponsePagination<T> {

    private String message;
    private List<T> data;
    private LocalDateTime timestamp;
    private int statuscode;
    private Pagination pagination;
}
