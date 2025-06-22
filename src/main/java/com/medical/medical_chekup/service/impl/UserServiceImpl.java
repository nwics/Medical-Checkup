package com.medical.medical_chekup.service.impl;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.medical.medical_chekup.dao.TokenRepository;
import com.medical.medical_chekup.dao.UserRepository;
import com.medical.medical_chekup.dto.UserDTO;
import com.medical.medical_chekup.model.MUser;
import com.medical.medical_chekup.model.TToken;
import com.medical.medical_chekup.service.TokenService;
import com.medical.medical_chekup.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    // private final TokenService tokenService;
    private final TokenRepository tokenRepository;

    private UserDTO mapDto(MUser user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setLastLogin(user.getLastLogin());
        userDTO.setBiodata(user.getBiodata());
        userDTO.setRole(user.getRole());

        return userDTO;
    }

    // @Override
    // public String generateOtp(Long id) {
    // Boolean foundUser = userRepository.existsByIdAndIsDeleteIsFalse(id);

    // throw new UnsupportedOperationException("Unimplemented method
    // 'generateOtp'");
    // }

    // @Override
    // public boolean verifyOtp(Long id, String otp) {
    // // TODO Auto-generated method stub
    // throw new UnsupportedOperationException("Unimplemented method 'verifyOtp'");
    // }

    @Override
    public String createPassword(String email, String password) {
        // TODO Auto-generated method stub

        MUser foundUserByEmail = userRepository.findByEmailAndIsDeleteIsFalse(email);
        boolean cekEmail = tokenRepository.existsByEmailAndIsDeleteIsFalse(email);
        if (cekEmail == true) {
            // MUser user = new MUser();
            foundUserByEmail.setModifiedBy(1L);
            foundUserByEmail.setModifiedOn(LocalDateTime.now());
            foundUserByEmail.setPassword(password);
            userRepository.save(foundUserByEmail);
            return "success create password";
        }
        return "failed create password";

        // throw new UnsupportedOperationException("Unimplemented method
        // 'createNewUser'");

    }

    @Override
    public UserDTO createUser(String email, UserDTO userDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createUser'");
    }

}
