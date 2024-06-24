package com.example.pharmacy_api.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private  String name;

    @Lob
    @Column(name = "imageData",length = 1048576)
    private byte[] imageDate;
}
