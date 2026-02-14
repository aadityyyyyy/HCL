package com.example.HMS.controller;

import com.example.HMS.dto.LoginRequest;
import com.example.HMS.dto.RegisterRequest;
import com.example.HMS.model.User;
import com.example.HMS.service.AuthService;
import lombok.RequiredArgsConstructor;
import com.example.HMS.security.JwtUtil;
import java.util.HashMap;
import java.util.Map;

import java.util.HashMap;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return "User registered successfully";
    }

   @PostMapping("/login")
public Map<String, Object> login(@RequestBody LoginRequest request) {

    User user = authService.login(request);
    String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

    Map<String, Object> response = new HashMap<>();
    response.put("token", token);
    response.put("user", user);

    return response;
}
private final JwtUtil jwtUtil;

}
