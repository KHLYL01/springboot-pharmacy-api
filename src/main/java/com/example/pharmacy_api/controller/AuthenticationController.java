package com.example.pharmacy_api.controller;

import com.example.pharmacy_api.model.dto.LoginRequest;
import com.example.pharmacy_api.model.dto.RefreshTokenRequest;
import com.example.pharmacy_api.model.dto.AdminRegisterRequest;
import com.example.pharmacy_api.model.dto.UserRegisterRequest;
import com.example.pharmacy_api.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping(value = "/register_admin")
    public ResponseEntity<?> registerAdmin(@RequestBody AdminRegisterRequest registerRequest) {
        return new ResponseEntity<>(authenticationService.registerAdmin(registerRequest), HttpStatus.CREATED);
    }

    @PostMapping("/refresh_admin")
    public ResponseEntity<?> refreshAdmin(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
    }

    @PostMapping(value = "/register_user")
    public ResponseEntity<?> registerUser(@RequestBody UserRegisterRequest registerRequest) {
        return new ResponseEntity<>(authenticationService.registerUser(registerRequest), HttpStatus.CREATED);
    }

    @PostMapping(value = "/login", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authenticationService.login(loginRequest));
    }

}
