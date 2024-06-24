package com.example.pharmacy_api.model.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AdminRegisterRequest {

    private String fullName;
    private String email;
    private String password;
    private String pharmacyName;
    private String region;
    private String phoneNumber;

}
