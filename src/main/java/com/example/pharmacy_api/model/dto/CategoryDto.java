package com.example.pharmacy_api.model.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryDto {
    private int id;
    private String name;
    private int adminId;
}
