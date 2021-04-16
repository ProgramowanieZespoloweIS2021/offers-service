package com.pz.offersservice.offers.controller;

import com.pz.offersservice.offers.exception.InvalidFilteringCriteriaException;
import com.pz.offersservice.offers.exception.InvalidOrderingCriteriaException;
import com.pz.offersservice.offers.exception.InvalidOfferIdentifierException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class OffersExceptionHandler {


    @ExceptionHandler(value = {
            InvalidOrderingCriteriaException.class,
            InvalidFilteringCriteriaException.class,
            InvalidOfferIdentifierException.class
    })
    public ResponseEntity<Object> handle(RuntimeException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            if(errors.containsKey(fieldName)) {
                errors.put(fieldName, errors.get(fieldName) + " " + errorMessage);
            }
            else {
                errors.put(fieldName, errorMessage);
            }

        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

}
