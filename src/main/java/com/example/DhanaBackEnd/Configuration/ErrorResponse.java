package com.example.DhanaBackEnd.Configuration;

import lombok.Data;

@Data
public class ErrorResponse {
    private String message;
    private boolean success;
}
