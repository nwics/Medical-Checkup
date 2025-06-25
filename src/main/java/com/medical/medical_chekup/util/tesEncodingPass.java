package com.medical.medical_chekup.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class tesEncodingPass {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        String rawPassword = "123masuk";
        String encodedPassword = encoder.encode(rawPassword);

        System.out.println("Encoded password: " + encodedPassword);
    }
}
