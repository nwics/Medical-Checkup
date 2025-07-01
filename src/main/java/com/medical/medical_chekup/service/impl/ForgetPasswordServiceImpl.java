package com.medical.medical_chekup.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.medical.medical_chekup.dao.ResetPasswordRepository;
import com.medical.medical_chekup.dao.TokenRepository;
import com.medical.medical_chekup.dao.UserRepository;
import com.medical.medical_chekup.dto.TokenDTO;
import com.medical.medical_chekup.model.MUser;
import com.medical.medical_chekup.model.TResetPassword;
import com.medical.medical_chekup.model.TToken;
import com.medical.medical_chekup.service.ForgetPasswordService;
import com.medical.medical_chekup.service.RegisterService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ForgetPasswordServiceImpl implements ForgetPasswordService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final ResetPasswordRepository resetPasswordRepository;
    private final RegisterService registerService;

    private TokenDTO mapTokenDTO(TToken token) {
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setId(token.getId());
        tokenDTO.setExpired(token.isExpired());
        tokenDTO.setExpiredOn(token.getExpiredOn());
        tokenDTO.setToken(token.getToken());
        tokenDTO.setUserFor(token.getUsedFor());
        return tokenDTO;
    }

    @Override
    public TokenDTO createNewOtp(String email) {

        String otp = String.format("%06d", new Random().nextInt(999999));
        LocalDateTime expireAt = LocalDateTime.now().plusMinutes(10L);
        boolean foundEmail = tokenRepository.existsByEmailAndIsDeleteIsFalse(email);
        if (foundEmail == false) {
            throw new RuntimeException("email was not found");
        }
        TToken newToken = new TToken();
        newToken.setEmail(email);
        // newToken.setToken(UUID.randomUUID().toString());
        newToken.setExpiredOn(expireAt);
        newToken.setCreatedOn(LocalDateTime.now());
        newToken.setToken(otp);
        newToken.setUsedFor("forgot password");

        try {
            registerService.sendOtpToEmail(email, otp);
            tokenRepository.save(newToken);
            return mapTokenDTO(newToken);
        } catch (Exception e) {
            // TODO: handle exception
            throw new RuntimeException("failed create otp" + e.getMessage());
        }

    }

    @Override
    public String verifyOtp(String email, String token) {

        List<TToken> foundTokenByEmail = tokenRepository.findByEmailAndIsExpiredIsFalse(email);

        TToken temp = foundTokenByEmail.get(0);
        if (temp.getToken().trim().equals(token) && temp.getExpiredOn().isAfter(LocalDateTime.now())) {
            temp.setExpired(true);
            tokenRepository.save(temp);
            return "success";
        } else {
            return "failed";
        }
    }

    @Override
    public String createPassword(String password, String setPassword, String email) {
        MUser foundUser = userRepository.findByEmailAndIsDeleteIsFalse(email).orElse(null);
        if (foundUser == null) {
            throw new RuntimeException("email was not found");
        }
        if (!password.equals(setPassword)) {
            throw new RuntimeException("password and set password not match");
        }
        if (!validatePassword(password)) {
            throw new RuntimeException(
                    "password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one number, and one special character");
        }
        foundUser.setPassword(password);

        userRepository.save(foundUser);
        return "success create password";
    }

    // validate password di dto harusnya
    public boolean validatePassword(String password) {
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        return password != null && password.matches(regex);
    }

}
