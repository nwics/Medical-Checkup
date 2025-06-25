package com.medical.medical_chekup.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.medical.medical_chekup.dao.TokenRepository;
import com.medical.medical_chekup.dao.UserRepository;
import com.medical.medical_chekup.dto.MyUserPrincipal;
import com.medical.medical_chekup.dto.UserDTO;
import com.medical.medical_chekup.model.MUser;
import com.medical.medical_chekup.model.TToken;
import com.medical.medical_chekup.service.TokenService;
import com.medical.medical_chekup.service.UserServicewrong;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    // private final TokenService tokenService;
    private final TokenRepository tokenRepository;

    private PasswordEncoder passwordEncoder;

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

    // @Override
    // public String createPassword(String email, String password) {
    // // TODO Auto-generated method stub

    // MUser foundUserByEmail = userRepository.findByEmailAndIsDeleteIsFalse(email);
    // boolean cekEmail = tokenRepository.existsByEmailAndIsDeleteIsFalse(email);
    // if (cekEmail == true) {
    // // MUser user = new MUser();
    // foundUserByEmail.setModifiedBy(1L);
    // foundUserByEmail.setModifiedOn(LocalDateTime.now());
    // foundUserByEmail.setPassword(password);
    // userRepository.save(foundUserByEmail);
    // return "success create password";
    // }
    // return "failed create password";

    // }

    // @Override
    // public UserDTO createUser(String email, UserDTO userDTO) {
    // // TODO Auto-generated method stub
    // throw new UnsupportedOperationException("Unimplemented method 'createUser'");
    // }

    public List<MUser> findAll() {
        // System.out.println("=== DEBUG: Executing findAll in service ===");
        List<MUser> users = userRepository.findAll();
        // System.out.println("=== DEBUG: Found " + users.size() + " users ===");
        return users;

        // return this.userRepository.findAll();
    }

    public MUser findById(Long id) {
        return this.userRepository.findById(id).orElse(null);
    }

    public MUser save(MUser user) {
        MUser newUser = new MUser();
        newUser.setPassword(user.getPassword());
        return this.userRepository.save(newUser);
    }

    public MUser update(Long id, MUser user) {
        MUser foundUser = this.userRepository.findById(id).orElse(null);

        foundUser.setBiodata(user.getBiodata());
        foundUser.setEmail(user.getEmail());
        foundUser.setRole(user.getRole());
        return this.userRepository.save(foundUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return this.userRepository.findByEmailAndIsDeleteIsFalse(username).map(user -> new MyUserPrincipal(user))
                .orElseThrow(() -> new UsernameNotFoundException("username not found : " + username));
    }

}
