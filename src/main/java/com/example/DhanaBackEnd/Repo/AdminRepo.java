package com.example.DhanaBackEnd.Repo;

import com.example.DhanaBackEnd.Model.Admin;
import com.example.DhanaBackEnd.Model.Login;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepo extends JpaRepository<Admin, Long> {
}
