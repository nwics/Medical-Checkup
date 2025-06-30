package com.medical.medical_chekup.security;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
// import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.stereotype.Service;

import com.medical.medical_chekup.converter.UserToUserDtoConverter;
import com.medical.medical_chekup.dao.UserRepository;
import com.medical.medical_chekup.dto.MyUserPrincipal;
import com.medical.medical_chekup.dto.UserDTO;
import com.medical.medical_chekup.model.MUser;

@Service
public class AuthService {

    private final JwtProvider jwtProvider;

    private final UserToUserDtoConverter userDtoConverter;

    private final UserRepository userRepository;

    public AuthService(JwtProvider jwtProvider, UserToUserDtoConverter userDtoConverter,
            UserRepository userRepository) {
        this.jwtProvider = jwtProvider;
        this.userDtoConverter = userDtoConverter;
        this.userRepository = userRepository;
    }

    public Map<String, Object> createLoginInfo(Authentication authentication) {
        MyUserPrincipal principal = (MyUserPrincipal) authentication.getPrincipal();
        Long userId = principal.getMUser().getId();
        MUser foundUser = userRepository.findById(userId).orElse(null);
        if (foundUser == null) {
            throw new RuntimeException("User not found");
        }
        // MUser mUser = principal.getMUser();
        foundUser.setLastLogin(LocalDateTime.now());
        this.userRepository.save(foundUser);
        UserDTO userDTO = this.userDtoConverter.convert(foundUser);

        String token = this.jwtProvider.createToken(authentication);

        Map<String, Object> loginResultMap = new HashMap<>();

        loginResultMap.put("userInfo", userDTO);
        loginResultMap.put("token", token);

        return loginResultMap;
    }

    // private final
}
