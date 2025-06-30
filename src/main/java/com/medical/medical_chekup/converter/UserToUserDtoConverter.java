package com.medical.medical_chekup.converter;

import java.time.LocalDateTime;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.medical.medical_chekup.dto.UserDTO;
import com.medical.medical_chekup.model.MUser;

@Component
public class UserToUserDtoConverter implements Converter<MUser, UserDTO> {

    @Override
    @Nullable
    public UserDTO convert(MUser source) {
        // TODO Auto-generated method stub
        UserDTO userDTO = new UserDTO();
        userDTO.setId(source.getId());
        userDTO.setBiodata(source.getBiodata());
        userDTO.setLastLogin(source.getLastLogin());
        userDTO.setEmail(source.getEmail());
        userDTO.setRole(source.getRole());
        return userDTO;
    }

}
