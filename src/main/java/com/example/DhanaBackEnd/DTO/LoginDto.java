package com.example.DhanaBackEnd.DTO;

import lombok.Data;

@Data
public class LoginDto {
    private String password;
    private  String email;
    private String name;
    private String role;
    private boolean active;

}
