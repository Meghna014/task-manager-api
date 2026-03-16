package com.example.taskmanager.controller;

import com.example.taskmanager.entity.User;
import com.example.taskmanager.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final UserRepository repo;
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    // token -> username
    private final Map<String, String> sessions = new ConcurrentHashMap<>();

    public AuthController(UserRepository repo) {
        this.repo = repo;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {

        if(repo.findByUsername(user.getUsername()).isPresent())
            return ResponseEntity.badRequest().body("Username already exists");

        repo.save(user);
        return ResponseEntity.ok("User registered");
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginUser){

        var userOpt = repo.findByUsername(loginUser.getUsername());

        if(userOpt.isEmpty() ||
                !userOpt.get().getPassword().equals(loginUser.getPassword()))
            return ResponseEntity.status(401).body("Invalid credentials");

        String token = UUID.randomUUID().toString();
        sessions.put(token, loginUser.getUsername());
        log.info("the token for user: " + loginUser.getUsername() + " and token is:  " + token);
        return ResponseEntity.ok(token);

    }

    @PostMapping("/logout")
    public void logout(@RequestHeader("Authorization") String header){
        String token = extractToken(header);
        sessions.remove(token);
    }

    private String extractToken(String header){
            if(header == null) return null;
            if(header.startsWith("Bearer "))
                return header.substring(7);
            return header;

    }


    // helper for next step
    public boolean isValidToken(String token){
        if(token == null) return false;
        if(token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        return sessions.containsKey(token);
    }

    public String getUsername(String token){
        return sessions.get(token);
    }
}