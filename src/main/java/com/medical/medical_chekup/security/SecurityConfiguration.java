package com.medical.medical_chekup.security;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import lombok.RequiredArgsConstructor;

@Configuration
// @RequiredArgsConstructor
public class SecurityConfiguration {

    private final RSAPublicKey publicKey;
    private final RSAPrivateKey privateKey;

    @Value("${api.base.url}")
    private String baseUrl;

    private final CustomBasicAuthenticationEntryPoint customBasicAuthenticationEntryPoint;
    private final CustomBearearTokenAccessDeniedHandler customBearearTokenAccessDeniedHandler;
    private final CustomBearearTokenAuthenticationEntryPoint customBearearTokenAuthenticationEntryPoint;

    public SecurityConfiguration(
            CustomBasicAuthenticationEntryPoint customBasicAuthenticationEntryPoint,
            CustomBearearTokenAccessDeniedHandler customBearearTokenAccessDeniedHandler,
            CustomBearearTokenAuthenticationEntryPoint customBearearTokenAuthenticationEntryPoint)
            throws NoSuchAlgorithmException {

        // generate public/private key pair
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        this.publicKey = (RSAPublicKey) keyPair.getPublic();
        this.privateKey = (RSAPrivateKey) keyPair.getPrivate();
        // this.baseUrl = baseUrl;

        this.customBasicAuthenticationEntryPoint = customBasicAuthenticationEntryPoint;
        this.customBearearTokenAccessDeniedHandler = customBearearTokenAccessDeniedHandler;
        this.customBearearTokenAuthenticationEntryPoint = customBearearTokenAuthenticationEntryPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers(HttpMethod.GET, this.baseUrl + "/pasien/**").permitAll()
                        .requestMatchers(HttpMethod.POST, this.baseUrl + "/token/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, this.baseUrl + "/token/**").permitAll()
                        .requestMatchers(HttpMethod.GET, this.baseUrl + "/users/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, this.baseUrl + "/users").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, this.baseUrl + "/users/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, this.baseUrl + "/users/**").hasAuthority("ADMIN")
                        .anyRequest().authenticated())
                .headers(headers -> headers.frameOptions().disable())
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .httpBasic(httpBasic -> httpBasic.authenticationEntryPoint(this.customBasicAuthenticationEntryPoint))
                .oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                        .authenticationEntryPoint(this.customBearearTokenAuthenticationEntryPoint)
                        .accessDeniedHandler(this.customBearearTokenAccessDeniedHandler))
                .sessionManagement(
                        sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(this.publicKey).privateKey(this.privateKey).build();
        JWKSource<SecurityContext> jwkSet = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwkSet);
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(this.publicKey).build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");

        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

}
