package com.pz.offersservice.offers.controller;

import com.pz.offersservice.offers.exception.InvalidOrderingCriteriaException;
import com.pz.offersservice.offers.exception.OfferArchivedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class OffersExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {InvalidOrderingCriteriaException.class, OfferArchivedException.class})
    public ResponseEntity<Object> handle(RuntimeException exception, WebRequest request) {
        return handleExceptionInternal(
                exception,
                exception.getMessage(),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request);
    }

}
