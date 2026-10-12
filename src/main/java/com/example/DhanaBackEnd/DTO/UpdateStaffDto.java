package com.example.DhanaBackEnd.DTO;

import lombok.Data;

@Data
public class UpdateStaffDto {
    private String phone;
    private String name;
    private String  designation;
    private String department;
    private String email;

}
