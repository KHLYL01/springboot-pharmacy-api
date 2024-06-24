package com.example.pharmacy_api.model.dto;

import com.example.pharmacy_api.model.role.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class JwtAuthenticationResponse {

    private int id;
    private String name;
    private String pharmacyName;
    private String email;
    private String region;
    private String phoneNumber;
    private String token;
    private String refreshToken;
    private Role role;

}
