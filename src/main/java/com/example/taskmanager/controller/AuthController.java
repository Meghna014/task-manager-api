package com.example.taskmanager.controller;

import com.example.taskmanager.security.JwtUtil;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController

public class AuthController {

    private JwtUtil jwtUtil;
    private final String USERNAME = "admin";
    private final String PASSWORD = "admin123";
    private final String TOKEN = "token";

    public AuthController(JwtUtil jwtUtil)
    {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public Map<String,String> login(@RequestParam  String username , @RequestParam String password)
    {

        String token  = Strings.EMPTY;
            if(USERNAME.equals(username) && PASSWORD.equals(password))
            {
                 token = jwtUtil.createToken(username);
            }
            else
            {
                throw new RuntimeException("Invalid credentials");
            }

            return Map.of(TOKEN,token);
    }
}
