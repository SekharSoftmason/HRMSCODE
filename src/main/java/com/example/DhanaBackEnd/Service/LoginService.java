package com.example.DhanaBackEnd.Service;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.example.DhanaBackEnd.Configuration.JWTService;
import com.example.DhanaBackEnd.Configuration.ResponseGlobal;
import com.example.DhanaBackEnd.Configuration.SecurityConfig;
import com.example.DhanaBackEnd.DTO.LoginDto;
import com.example.DhanaBackEnd.Model.Login;
import com.example.DhanaBackEnd.Repo.LoginRepo;
import com.example.DhanaBackEnd.RoleEnum.RoleEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private static final Logger log = LoggerFactory.getLogger(LoginService.class);
    @Autowired
    private LoginRepo loginrepo;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private SecurityConfig securityConfig;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseGlobal<?> createAdmin(String email, String password, String name, String secretKey) {
        if (secretKey == null || secretKey.isEmpty()) {
            return ResponseGlobal.onFailure("Invalid secret Key");
        }
        Login UserExistOrNot = loginrepo.findByEmail(email);
        if (UserExistOrNot != null) {
            return ResponseGlobal.onFailure("Email already exists");
        }
        Login admin = new Login();
        admin.setEmail(email);
        admin.setName(name);
        admin.setPassword(password);
        admin.setRole(String.valueOf(RoleEnum.ADMIN));
        admin.setActive(true);
        Login savedAdmin =loginrepo.save(admin);
        LoginDto response = new LoginDto();

        response.setEmail(savedAdmin.getEmail());
        response.setName(savedAdmin.getName());
        response.setRole(savedAdmin.getRole());
        response.setActive(savedAdmin.isActive());
        return ResponseGlobal.onSuccess(
                "Admin created successfully", response
        );
    }
    public ResponseGlobal<?> login(String email, String password) {

        if (email == null || email.isBlank()
                || password == null || password.isBlank()) {
            return  ResponseGlobal.onError("Email and password are required");
        }
        Login user = loginrepo.findByEmail(email.trim().toLowerCase());
        if (user == null) {
            return  ResponseGlobal.onFailure("User not found");
        }
        if (!passwordEncoder.matches(
                password, user.getPassword())) {
            return ResponseGlobal.onFailure("Invalid password");
        }
        String token = jwtService.generateToken(user.getEmail(), user.getName(), user.getRole());

        return ResponseGlobal.onSuccess("Login successful",token
        );
    }
}

