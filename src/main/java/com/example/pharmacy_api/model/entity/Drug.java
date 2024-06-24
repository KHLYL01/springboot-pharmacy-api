package com.example.pharmacy_api.model.entity;


import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Drug {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private String name;

    private String imagePath;

    private int price;

    private int quantity;

    private String gauge;

    private String form;

    private String companyName;

    private String scientificName;

    private boolean isRequiredPrescription;

}
