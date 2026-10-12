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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private static final Logger log= LoggerFactory.getLogger(LoginService.class);
    @Autowired
    private LoginRepo loginrepo;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private SecurityConfig securityConfig;



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
        LoginDto response = new LoginDto();

        response.setEmail(admin.getEmail());
        response.setName(admin.getName());
        response.setRole(admin.getRole());
        response.setActive(admin.isActive());
        response.setPassword(admin.getPassword());
        loginrepo.save(admin);
        return ResponseGlobal.onSuccess(
                "Admin created successfully", admin
        );
    }

    public ResponseGlobal<?> login(String email, String password, String role) {
        if (email == null || password == null) {
            return ResponseGlobal.onError("Email and  password must not be null");
        }
        Login UserExistOrNot = loginrepo.findByEmail(email);
        if (UserExistOrNot == null) {
            return ResponseGlobal.onFailure("Invalid password for email " + email);
        }
        Login CheckPassword = loginrepo.findByEmailAndPassword(email, password);
        if (CheckPassword == null) {
            return ResponseGlobal.onFailure("Invalid password for email " + password);
        }
        if (role != null && !role.isBlank()) {
            return ResponseGlobal.onFailure("Invalid role");

        }
        String jwt = jwtService.generateToken(email, UserExistOrNot.getName(), role);
        return ResponseGlobal.onSuccess("Login Successful", jwt);
    }

}

