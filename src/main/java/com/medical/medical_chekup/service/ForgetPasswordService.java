package com.medical.medical_chekup.service;

import com.medical.medical_chekup.dto.TokenDTO;
import com.medical.medical_chekup.model.MUser;
import com.medical.medical_chekup.model.TResetPassword;
import com.medical.medical_chekup.model.TToken;

import jakarta.mail.MessagingException;

public interface ForgetPasswordService {

    // String sendOtpToEmail(String email, String otp) throws MessagingException;

    TokenDTO createNewOtp(String email);

    String verifyOtp(String email, String token);

    String createPassword(String password, String setPassword, String email);

    // MUser setBiodataUser(String email, MUser user);
}
