package com.medical.medical_chekup.restcontroller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medical.medical_chekup.dto.CustomNominalSaldoDTO;
import com.medical.medical_chekup.dto.DefaultSaldoDTO;
import com.medical.medical_chekup.dto.response.ApiResponse;
import com.medical.medical_chekup.model.TCustomerCustomNominal;
import com.medical.medical_chekup.service.BalanceWithdrawService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/balance-withdraw")
@RequiredArgsConstructor
public class ApiBalanceWithdrawController {

    private final BalanceWithdrawService balanceWithdrawService;

    @GetMapping("/default-saldo")
    public ResponseEntity<ApiResponse<List<DefaultSaldoDTO>>> getAllSaldo() {
        List<DefaultSaldoDTO> defaultSaldoDTOs = balanceWithdrawService.getAllSaldo();
        ApiResponse<List<DefaultSaldoDTO>> response = new ApiResponse<>();
        response.setMessage("success get all saldo");
        response.setData(defaultSaldoDTOs);
        response.setStatuscode(200);
        response.setTimestamp(LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create-saldo")
    public ResponseEntity<ApiResponse<TCustomerCustomNominal>> createCustomNominal(
            @RequestBody CustomNominalSaldoDTO customNominal) {

        TCustomerCustomNominal response = balanceWithdrawService.creatCustomerCustomNominal(customNominal);

        ApiResponse<TCustomerCustomNominal> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("success add custom nominal");
        apiResponse.setData(response);
        apiResponse.setStatuscode(HttpStatus.OK.value());
        apiResponse.setTimestamp(LocalDateTime.now());
        return ResponseEntity.ok(apiResponse);
    }

}
