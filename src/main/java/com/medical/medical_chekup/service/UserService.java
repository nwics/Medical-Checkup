package com.medical.medical_chekup.service;

import com.medical.medical_chekup.dto.UserDTO;

public interface UserService {

    String generateOtp(Long id);

    boolean verifyOtp(Long id, String otp);
}
