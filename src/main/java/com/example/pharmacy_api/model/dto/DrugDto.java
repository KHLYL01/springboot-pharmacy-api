package com.example.pharmacy_api.model.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DrugDto {

    private int id;

    private String name;

    private String imagePath;

    private int price;

    private int quantity;

    private String gauge;

    private String form;

    private String companyName;

    private String scientificName;

    private boolean isRequiredPrescription;

    private int categoryId;
}
