package com.example.DhanaBackEnd.Model;

import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
@Table(name="admin")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="Email",unique = true,nullable = false)
    private String email;
    @Column(unique=false)
    private String name;
}
