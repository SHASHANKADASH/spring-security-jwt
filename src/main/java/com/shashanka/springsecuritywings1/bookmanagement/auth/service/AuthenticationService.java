package com.shashanka.springsecuritywings1.bookmanagement.auth.service;

import com.shashanka.springsecuritywings1.bookmanagement.auth.JwtService;
import com.shashanka.springsecuritywings1.bookmanagement.auth.domain.AuthRequest;
import com.shashanka.springsecuritywings1.bookmanagement.auth.domain.AuthResponse;
import com.shashanka.springsecuritywings1.bookmanagement.auth.domain.RegisterRequest;
import com.shashanka.springsecuritywings1.bookmanagement.user.entity.Role;
import com.shashanka.springsecuritywings1.bookmanagement.user.entity.User;
import com.shashanka.springsecuritywings1.bookmanagement.user.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthResponse register(RegisterRequest request) {
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        userRepository.save(user);
        String token = jwtService.generateToken(user, List.of(request.getRole()));
        return AuthResponse.builder().token(token).build();
    }

    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        String token = jwtService.generateToken(user, List.of(user.getRole()));
        return AuthResponse.builder().token(token).build();
    }
}
