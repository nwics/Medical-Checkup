package com.medical.medical_chekup.dto;

import java.math.BigInteger;

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
public class DefaultSaldoDTO {

    private Long id;
    private BigInteger saldo;
    private String type;
}
