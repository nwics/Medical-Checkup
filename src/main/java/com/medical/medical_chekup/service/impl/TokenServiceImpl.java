package com.medical.medical_chekup.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.medical.medical_chekup.dao.TokenRepository;
import com.medical.medical_chekup.dao.UserRepository;
import com.medical.medical_chekup.dto.TokenDTO;
import com.medical.medical_chekup.model.MUser;
import com.medical.medical_chekup.model.TToken;
import com.medical.medical_chekup.service.TokenService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final UserRepository userRepository;

    private final TokenRepository tokenRepository;
    private final JavaMailSender mailSender;

    private TokenDTO mapDto(TToken token) {
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setId(token.getId());
        tokenDTO.setExpired(token.isExpired());
        tokenDTO.setExpiredOn(token.getExpiredOn());
        tokenDTO.setToken(token.getToken());
        tokenDTO.setUserFor(token.getUsedFor());
        // tokenDTO.setCustomerId(token.getCustomer().getId());

        return tokenDTO;
    }

    @Override
    public String sendOtpToEmail(String email, String otp) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(email);
        helper.setSubject("Your OTP CODE ");
        helper.setText("YOUR OTP CODE IS: " + otp);

        mailSender.send(message);

        return "success";

    }

    @Override
    public TokenDTO createNewOtp(String email) {
        // TODO Auto-generated method stub
        String otp = String.format("%06d", new Random().nextInt(999999));
        LocalDateTime expireAt = LocalDateTime.now().plusMinutes(10L);

        boolean foundEmail = tokenRepository.existsByEmailAndIsDeleteIsFalse(email);

        if (foundEmail == true) {
            throw new RuntimeException("email was registered");
        }
        // create user
        MUser user = new MUser();
        user.setEmail(email);
        user.setCreatedOn(LocalDateTime.now());
        user.setCreatedBy(1L);
        user.setIsDelete(false);
        user.setIsLocked(false);
        userRepository.save(user);

        TToken token = new TToken();
        token.setEmail(email);
        token.setExpiredOn(expireAt);
        token.setToken(otp);
        token.setCreatedBy(1L);
        token.setCreatedOn(LocalDateTime.now());
        token.setUsedFor("Registration");
        try {
            sendOtpToEmail(email, otp);

            tokenRepository.save(token);

            return mapDto(token);
        } catch (Exception e) {
            // TODO: handle exception
            throw new RuntimeException("failed create otp" + e.getMessage());
        }
        // throw new UnsupportedOperationException("Unimplemented method
        // 'createNewOtp'");
    }

    @Override
    public String verifyOtp(String email, String otp) {
        List<TToken> foundTokenByEmail = tokenRepository.findByEmailAndIsExpiredIsFalse(email);

        TToken temp = foundTokenByEmail.get(0);
        if (temp.getToken().trim().equals(otp) && temp.getExpiredOn().isAfter(LocalDateTime.now())) {
            temp.setExpired(true);
            tokenRepository.save(temp);
            return "success";
        } else {
            return "failed";
        }
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'verifyOtp'");

    }

}
