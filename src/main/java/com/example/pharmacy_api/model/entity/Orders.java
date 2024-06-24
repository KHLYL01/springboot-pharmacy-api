package com.example.pharmacy_api.model.entity;


import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    private ImageOrder imageOrder;

    private String status;

    private double totalPrice;

}
