package com.example.DhanaBackEnd.Repo;

import com.example.DhanaBackEnd.Model.Login;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginRepo extends JpaRepository<Login, Long> {
     Login findByEmail(String email);
     boolean existsByEmail(String email);

    Login findByEmailAndPassword(String email, String password);

    long countByRoleAndActive(String staff, boolean b);

}
