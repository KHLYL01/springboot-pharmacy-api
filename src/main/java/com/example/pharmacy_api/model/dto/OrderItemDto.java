package com.example.pharmacy_api.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@Builder
public class OrderItemDto {

    private int ordersId;

    private int drugId;

    private String drugName;

    private int quantity;

    private int price;
}
