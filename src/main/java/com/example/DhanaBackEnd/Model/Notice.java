package com.example.DhanaBackEnd.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name="/notices")
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String noticeTitle;
    private String noticeName;
    private String noticeDescription;
    private String noticeImage;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ADMIN_ID", nullable = false)
    private Admin admin;
}
