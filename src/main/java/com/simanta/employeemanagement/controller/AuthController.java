package com.simanta.employeemanagement.controller;

import com.simanta.employeemanagement.dto.AuthRequest;
import com.simanta.employeemanagement.dto.AuthResponse;
import com.simanta.employeemanagement.entity.User;
import com.simanta.employeemanagement.repository.UserRepository;
import com.simanta.employeemanagement.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jtwUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AuthRequest request){
        if(userRepository.findByUsername(request.getUsername()).isPresent()){
            return ResponseEntity.badRequest().body("Username already exist");
        }

        User user = new User(request.getUsername(),
               passwordEncoder.encode( request.getPassword()),
                "ROLE_USER"
                );
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User Is Successfully Registered");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        String token = jtwUtil.generateToken(authentication.getName());

        return ResponseEntity.ok(new AuthResponse(token));
    }

}
