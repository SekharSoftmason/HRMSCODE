package com.example.DhanaBackEnd.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
@Entity
@Data
@Table(name="holidays")
public class Holiday {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDate holidayDate;
    private String holidayName;
    private String holidayDescription;
    private String holidayImage;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ADMIN_ID", nullable = false)
    private Admin admin;

}
