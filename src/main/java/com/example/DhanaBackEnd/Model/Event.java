package com.example.DhanaBackEnd.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
@Entity
@Data
@Table(name="event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDate eventDate;
    private String eventName;
    private String eventDescription;
    private String eventImage;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ADMIN_ID", nullable = false)
    private Admin admin;
}
