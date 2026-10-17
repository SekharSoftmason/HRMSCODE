package com.example.DhanaBackEnd.Repo;

import com.example.DhanaBackEnd.Model.Login;
import com.example.DhanaBackEnd.Model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StaffRepo extends JpaRepository<Staff, Long> {
    Staff findByEmail(String email);

}
