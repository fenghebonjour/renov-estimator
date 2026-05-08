package com.renovSolution.renov.controller;

import com.renovSolution.renov.dto.AuthRequest;
import com.renovSolution.renov.dto.AuthResponse;
import com.renovSolution.renov.model.User;
import com.renovSolution.renov.repo.UserRepo;
import com.renovSolution.renov.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final UserRepo userRepo;

    public AuthController(AuthenticationManager authManager, UserDetailsService userDetailsService,
                          JwtUtil jwtUtil, UserRepo userRepo) {
        this.authManager = authManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.userRepo = userRepo;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtUtil.generateToken(userDetails);
        User user = userRepo.findByUsername(request.getUsername()).orElseThrow();
        return ResponseEntity.ok(new AuthResponse(token, user.getId(), user.getType()));
    }
}
