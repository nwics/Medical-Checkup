package com.medical.medical_chekup.service;

import com.medical.medical_chekup.dto.TokenDTO;

import jakarta.mail.MessagingException;

public interface TokenService {
    String sendOtpToEmail(String email, String otp) throws MessagingException;

    TokenDTO createNewOtp(String email);

    boolean verifyOtp(String email, String otp);

}
