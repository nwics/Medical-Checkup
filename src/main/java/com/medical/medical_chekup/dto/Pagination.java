package com.medical.medical_chekup.dto;

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
public class Pagination {

    private Integer size;
    private Long total;
    private Integer totalPages;
    private Integer current;
    private Filter filter;
}
