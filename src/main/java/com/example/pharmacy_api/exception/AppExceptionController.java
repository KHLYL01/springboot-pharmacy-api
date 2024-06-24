package com.example.pharmacy_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@ControllerAdvice
public class AppExceptionController{


    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ExceptionResponse> methodArgumentNotValidHandle(MethodArgumentNotValidException ex){

        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).toList();

        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .timestamp(new Date())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(errors.toString())
                .build();

        return new ResponseEntity<>(exceptionResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {SQLIntegrityConstraintViolationException.class})
    public ResponseEntity<ExceptionResponse> sqlIntegrityConstraintViolationHandle(SQLIntegrityConstraintViolationException ex){

        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .timestamp(new Date())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(ex.getLocalizedMessage())
                .build();

        return new ResponseEntity<>(exceptionResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {NoSuchElementException.class})
    public ResponseEntity<ExceptionResponse> noSuchElementHandle(NoSuchElementException ex){

        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .timestamp(new Date())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(ex.getLocalizedMessage())
                .build();

        return new ResponseEntity<>(exceptionResponse,HttpStatus.BAD_REQUEST);
    }


}
