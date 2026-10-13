package com.example.DhanaBackEnd.Controller;

import com.example.DhanaBackEnd.Configuration.ResponseGlobal;
import com.example.DhanaBackEnd.Service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/create-Admin")
    public ResponseGlobal<?> createAdmin(@RequestParam String email, @RequestParam String password, @RequestParam String name, @RequestParam String secretKey) {
        return loginService.createAdmin(email, password, name, secretKey);
    }

    @PostMapping("/login")
    public ResponseGlobal<?> login(@RequestParam String email, @RequestParam String password) {
        return loginService.login(email, password);
    }
}
