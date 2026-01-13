package com.university.bookings.controller;

import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.university.bookings.Entities.Role;
import com.university.bookings.Entities.User;
import com.university.bookings.dto.LoginResponse;
import com.university.bookings.repository.UserRepository;
import com.university.bookings.security.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager,
                          UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.CUSTOMER);
        userRepository.save(user);
        return "User registered successfully";
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody User user) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        user.getPassword()
                )
        );

        String token = jwtUtil.generateToken(user.getEmail());

        return new LoginResponse(token);
    }
}
