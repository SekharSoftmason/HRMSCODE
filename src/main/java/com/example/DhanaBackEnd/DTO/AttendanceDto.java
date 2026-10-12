package com.example.DhanaBackEnd.DTO;

import com.example.DhanaBackEnd.Model.Staff;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
public class AttendanceDto {
    private long id;
    private long staffId;
    private Staff staffName;
    private LocalDate attendanceDate;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private Double workingHours;
}
