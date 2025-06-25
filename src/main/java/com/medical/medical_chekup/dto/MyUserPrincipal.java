package com.medical.medical_chekup.dto;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import com.medical.medical_chekup.model.MUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

public class MyUserPrincipal implements UserDetails {

    private MUser user;

    public MyUserPrincipal(MUser user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method
        // 'getAuthorities'");
        return Arrays.stream(StringUtils.tokenizeToStringArray(this.user.getRole().getCode(), " "))
                .map(role -> new SimpleGrantedAuthority(role))
                .toList();
    }

    @Override
    public String getPassword() {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method
        // 'getPassword'");
        return this.user.getPassword();

    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method
        // 'getUsername'");
        return this.user.getEmail();
    }

    public MUser getMUser() {
        return user;
    }
}
