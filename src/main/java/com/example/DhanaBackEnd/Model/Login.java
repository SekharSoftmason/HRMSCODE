package com.example.DhanaBackEnd.Model;

import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
@Table(name="login")
public class Login {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
      private long id;
      @Column(unique = true, nullable = false)
      private String email;
      @Column(nullable = false)
      private String Password;
      @Column(nullable = false)
      private String name;
      @Column(nullable = false)
      private String role;
      private boolean active;

}
