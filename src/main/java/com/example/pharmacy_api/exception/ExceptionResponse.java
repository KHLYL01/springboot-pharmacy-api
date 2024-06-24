package com.example.pharmacy_api.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Builder
public class ExceptionResponse{

    private Date timestamp;
    private int statusCode;
    private String message;

}
