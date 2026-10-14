package com.example.DhanaBackEnd.Controller;

import com.example.DhanaBackEnd.Configuration.ResponseGlobal;
import com.example.DhanaBackEnd.DTO.AdminDto;
import com.example.DhanaBackEnd.DTO.LoginDto;
import com.example.DhanaBackEnd.DTO.LoginRequestDto;
import com.example.DhanaBackEnd.Service.LoginService;
import jakarta.persistence.ManyToOne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/create-Admin")
    public ResponseGlobal<AdminDto> createAdmin(@ModelAttribute AdminDto adminDto) {
        return loginService.createAdmin(adminDto);
    }

    @PostMapping("/login")
    public ResponseGlobal<?> login(@ModelAttribute LoginDto dto) {
        return loginService.login(dto);
    }
}
