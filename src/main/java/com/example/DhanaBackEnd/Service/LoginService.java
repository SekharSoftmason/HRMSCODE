package com.example.DhanaBackEnd.Service;

import com.example.DhanaBackEnd.Configuration.JWTService;
import com.example.DhanaBackEnd.Configuration.ResponseGlobal;
import com.example.DhanaBackEnd.Configuration.SecurityConfig;
import com.example.DhanaBackEnd.DTO.AdminDto;
import com.example.DhanaBackEnd.Model.Admin;
import com.example.DhanaBackEnd.Model.Login;
import com.example.DhanaBackEnd.Repo.AdminRepo;
import com.example.DhanaBackEnd.Repo.LoginRepo;
import com.example.DhanaBackEnd.RoleEnum.RoleEnum;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private static final Logger log = LoggerFactory.getLogger(LoginService.class);
    @Autowired
    private LoginRepo loginrepo;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private SecurityConfig securityConfig;
    @Autowired
    private AdminRepo adminrepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private String SecretKey="indhjdjgsioljgdyliool1234";
    public ResponseGlobal<AdminDto> createAdmin(AdminDto adminDTO) {
        if (adminDTO.getEmail() == null || adminDTO.getEmail().isEmpty()) {
            return ResponseGlobal.onFailure("Email is required for Admin creation.");
        }
        if (adminDTO.getPassword() == null || adminDTO.getPassword().isEmpty()) {
            return ResponseGlobal.onFailure("Password is required for Admin creation.");
        }
        if (adminDTO.getSecretKey() == null || !SecretKey.equals(adminDTO.getSecretKey())) {
           return ResponseGlobal.onFailure("Invalid Secret Key provided for Admin creation.");
        }

        boolean ifEmailExists = loginrepo.existsByEmail(adminDTO.getEmail());

        if(ifEmailExists){
            return ResponseGlobal.onFailure("This Email is already being used by another Admin or Student.");
        }
        Admin admin = new Admin();
        admin.setEmail(adminDTO.getEmail());
        admin.setName(adminDTO.getName());
        adminrepo.save(admin);

        Login login = new Login();
        login.setEmail(adminDTO.getEmail().trim());
        login.setPassword(adminDTO.getPassword());
        login.setName(adminDTO.getName());
        login.setRole(String.valueOf(RoleEnum.ADMIN));
        loginrepo.save(login);

        return ResponseGlobal.onSuccess("Admin has been created successfully.",adminDTO);
    }

    public ResponseGlobal<?> login(String email, String password) {
        if (email == null || email.isBlank() || password == null || password.isBlank()) {
            return ResponseGlobal.onFailure("Email and password are required");
        }
        Login user = loginrepo.findByEmail(email.trim());
        if (user == null || user.getPassword() == null) {
            return ResponseGlobal.onFailure("Invalid email or password");
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return ResponseGlobal.onFailure("Invalid email or password");
        }
        String token = jwtService.generateToken(
                user.getEmail(),
                user.getName(),user.getRole());
        return ResponseGlobal.onSuccess("Login successful", token);
    }
    }


