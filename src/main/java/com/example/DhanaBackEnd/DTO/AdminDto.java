package com.example.DhanaBackEnd.DTO;

import jakarta.persistence.Entity;
import lombok.Data;
@Data

public class AdminDto {
    private String email;
    private String name;
    private String password;
    private String SecretKey;
}
