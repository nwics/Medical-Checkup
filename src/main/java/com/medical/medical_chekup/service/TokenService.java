package com.medical.medical_chekup.service;

import com.medical.medical_chekup.dto.TokenDTO;
import com.medical.medical_chekup.model.MUser;

import jakarta.mail.MessagingException;

public interface TokenService {
    String sendOtpToEmail(String email, String otp) throws MessagingException;

    TokenDTO createNewOtp(String email);

    String verifyOtp(String email, String otp);

    String createPassword(String password, String email);

    MUser setBiodataUser(String email, MUser user);

}
