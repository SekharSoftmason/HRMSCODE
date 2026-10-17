package com.example.DhanaBackEnd.DTO;

import jakarta.persistence.Lob;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
@Data
public class HolidayDto {
    private Long adminId;
    private LocalDate holidayDate;
    private String holidayName;
    private String holidayDescription;
    private MultipartFile holidayImage;
}
