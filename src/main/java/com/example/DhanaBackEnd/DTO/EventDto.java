package com.example.DhanaBackEnd.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
public class EventDto {
    private Long adminId;
    private LocalDate eventDate;
    private String eventName;
    private String eventDescription;
    private MultipartFile eventImage;
}
