package com.shashanka.springsecuritywings1.bookmanagement.auth.controller;

import com.shashanka.springsecuritywings1.bookmanagement.auth.domain.AuthRequest;
import com.shashanka.springsecuritywings1.bookmanagement.auth.domain.AuthResponse;
import com.shashanka.springsecuritywings1.bookmanagement.auth.domain.RegisterRequest;
import com.shashanka.springsecuritywings1.bookmanagement.auth.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> register(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
