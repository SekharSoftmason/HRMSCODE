package com.example.DhanaBackEnd.Configuration;

import com.example.DhanaBackEnd.Exception.HRMSNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(HRMSNotFoundException.class)
    public ResponseGlobal<?> handlerHRMSNotFoundException(HRMSNotFoundException ex) {
             return ResponseGlobal.onError(ex.getMessage());
    }
}
