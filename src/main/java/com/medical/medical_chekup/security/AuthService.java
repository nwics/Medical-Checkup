package com.medical.medical_chekup.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
// import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.stereotype.Service;

import com.medical.medical_chekup.converter.UserToUserDtoConverter;
import com.medical.medical_chekup.dto.MyUserPrincipal;
import com.medical.medical_chekup.dto.UserDTO;
import com.medical.medical_chekup.model.MUser;

@Service
public class AuthService {

    private final JwtProvider jwtProvider;

    private final UserToUserDtoConverter userDtoConverter;

    public AuthService(JwtProvider jwtProvider, UserToUserDtoConverter userDtoConverter) {
        this.jwtProvider = jwtProvider;
        this.userDtoConverter = userDtoConverter;
    }

    public Map<String, Object> createLoginInfo(Authentication authentication) {
        MyUserPrincipal principal = (MyUserPrincipal) authentication.getPrincipal();
        MUser mUser = principal.getMUser();
        UserDTO userDTO = this.userDtoConverter.convert(mUser);

        String token = this.jwtProvider.createToken(authentication);

        Map<String, Object> loginResultMap = new HashMap<>();

        loginResultMap.put("userInfo", userDTO);
        loginResultMap.put("token", token);

        return loginResultMap;
    }

    // private final
}
