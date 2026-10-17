package com.example.DhanaBackEnd.Repo;

import com.example.DhanaBackEnd.Model.Admin;
import com.example.DhanaBackEnd.Model.Holiday;
import com.example.DhanaBackEnd.Model.Login;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminRepo extends JpaRepository<Admin, Long> {
    //List<Holiday> findById(long adminId);
}
