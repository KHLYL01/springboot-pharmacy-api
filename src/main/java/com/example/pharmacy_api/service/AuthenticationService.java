package com.example.pharmacy_api.service;

import com.example.pharmacy_api.jwt.JwtService;
import com.example.pharmacy_api.model.dto.*;
import com.example.pharmacy_api.model.entity.Admin;
import com.example.pharmacy_api.model.role.Role;
import com.example.pharmacy_api.model.entity.User;
import com.example.pharmacy_api.repository.AdminRepository;
import com.example.pharmacy_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final AdminRepository adminRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    @Transactional
    public JwtAuthenticationResponse registerAdmin(AdminRegisterRequest registerRequest) {
        User user = User.builder()
                .fullName(registerRequest.getFullName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.ADMIN)
                .build();

        User saveUser = userRepository.save(user);

        Admin admin = Admin.builder()
                .user(saveUser)
                .pharmacyName(registerRequest.getPharmacyName())
                .region(registerRequest.getRegion())
                .phoneNumber(registerRequest.getPhoneNumber())
                .build();
        Admin savedAdmin = adminRepository.save(admin);


        return JwtAuthenticationResponse.builder()
                .id(savedAdmin.getId())
                .name(saveUser.getFullName())
                .pharmacyName(savedAdmin.getPharmacyName())
                .email(saveUser.getEmail())
                .phoneNumber(savedAdmin.getPhoneNumber())
                .region(savedAdmin.getRegion())
                .role(saveUser.getRole())
                .build();
    }


    public JwtAuthenticationResponse login(LoginRequest loginRequest) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        var user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        if(adminRepository.findAdminByUser_Email(loginRequest.getEmail()) != null){
            Admin admin = adminRepository.findAdminByUser_Id(user.getId());

            return JwtAuthenticationResponse.builder()
                    .id(admin.getId())
                    .name(user.getFullName())
                    .pharmacyName(admin.getPharmacyName())
                    .email(user.getEmail())
                    .phoneNumber(admin.getPhoneNumber())
                    .region(admin.getRegion())
                    .role(user.getRole())
                    .token(jwt)
                    .refreshToken(refreshToken)
                    .build();
        }

        return JwtAuthenticationResponse.builder()
                .id(user.getId())
                .name(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole())
                .token(jwt)
                .refreshToken(refreshToken)
                .build();

    }

    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest){
        String username = jwtService.extractUsername(refreshTokenRequest.getToken());
        User user = userRepository.findByEmail(username).orElseThrow();
        if(jwtService.isTokenValid(refreshTokenRequest.getToken(),user)){
            String jwt = jwtService.generateToken(user);

            return JwtAuthenticationResponse.builder()
                    .token(jwt)
                    .refreshToken(refreshTokenRequest.getToken())
                    .build();
        }
        return null;
    }

    public JwtAuthenticationResponse registerUser(UserRegisterRequest registerRequest) {
        User user = User.builder()
                .fullName(registerRequest.getFullName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.USER)
                .build();

        User saveUser = userRepository.save(user);

        return JwtAuthenticationResponse.builder()
                .id(saveUser.getId())
                .name(saveUser.getFullName())
                .email(saveUser.getEmail())
                .role(saveUser.getRole())
                .build();
    }

}
