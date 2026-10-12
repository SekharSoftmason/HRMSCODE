package com.example.DhanaBackEnd.Model;

import com.example.DhanaBackEnd.Configuration.ResponseGlobal;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
@Data
@Entity
public class Staff {
       @Id
       @GeneratedValue(strategy = GenerationType.IDENTITY)
       private Long id;
       private String phone;
       @Column(nullable = false)
       private String name;
       private String  designation;
       private String department;
       @Column(unique = true, nullable=false)
       private String email;
       private LocalDate joiningDate;

}
