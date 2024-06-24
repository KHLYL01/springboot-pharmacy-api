package com.example.pharmacy_api.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Setter
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrdersDto {

    private int id;
    private int adminId;
    private String pharmacyName;
    private String status;
    private int userId;
    private double totalPrice;
    private List<OrderItemDto> orderItems;
    private String imageUrl;

}
