package com.example.pharmacy_api.model.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FavoriteDto {

    private int userId;

    private int drugId;
}
