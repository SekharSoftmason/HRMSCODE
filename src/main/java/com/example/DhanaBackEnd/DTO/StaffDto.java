package com.example.DhanaBackEnd.DTO;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDate;
@Data
public class StaffDto {
    private Long id;
    private String phone;
    private String name;
    private String  designation;
    private String department;
    private String email;
    private String password;
    private LocalDate joiningDate;

}
