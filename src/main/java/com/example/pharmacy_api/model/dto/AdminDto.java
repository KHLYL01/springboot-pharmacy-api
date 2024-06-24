package com.example.pharmacy_api.model.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AdminDto {
    private String name;

    private String phone;

    private String gender;

    private String pharmacyName;

    private int neighborhoodId;
}
