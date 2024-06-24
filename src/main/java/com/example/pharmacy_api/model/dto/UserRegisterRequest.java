package com.example.pharmacy_api.model.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRegisterRequest {
    private String fullName;
    private String email;
    private String password;
}
