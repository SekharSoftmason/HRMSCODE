package com.example.DhanaBackEnd.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name="attendance",uniqueConstraints ={@UniqueConstraint(columnNames={"staff_id","attendance_date"})} )
public class Attendance {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
     private long id;
     @ManyToOne
     @JoinColumn(name="staff_id", nullable = false)
     private Staff staff;
     @Column(name="attendance_date", nullable=false)
     private LocalDate attendanceDate;
     private LocalDateTime checkIn;
     private LocalDateTime checkOut;
     private Double workingHours;
}
