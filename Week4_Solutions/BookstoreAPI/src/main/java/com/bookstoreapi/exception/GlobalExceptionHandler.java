package com.bookstoreapi.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    public ResponseEntity<Map<String, String>> handleResourceNotFoundException(
            ResourceNotFoundException ex, WebRequest request) {
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("error", ex.getMessage());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "ResourceNotFound");
       
        return new ResponseEntity<>(errorDetails, headers, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidInputException.class)
    @ResponseBody
    public ResponseEntity<Map<String, String>> handleInvalidInputException(
            InvalidInputException ex, WebRequest request) {
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("error", ex.getMessage());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "InvalidInput");

        return new ResponseEntity<>(errorDetails, headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<Map<String, String>> handleGlobalException(
            Exception ex, WebRequest request) {
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("error", "An unexpected error occurred: " + ex.getMessage());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "GlobalException");
     
        return new ResponseEntity<>(errorDetails, headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
