package com.springBoot.blogApplication.services.impl;

import com.springBoot.blogApplication.entity.Role;
import com.springBoot.blogApplication.entity.User;
import com.springBoot.blogApplication.payload.AuthenticationResponse;
import com.springBoot.blogApplication.payload.LoginDTO;
import com.springBoot.blogApplication.repository.RoleRepository;
import com.springBoot.blogApplication.repository.UserRepository;
import com.springBoot.blogApplication.services.AuthService;
import com.springBoot.blogApplication.services.JWTService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImplementation implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository; // Add RoleRepository
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImplementation(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JWTService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(User request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Convert role names to Role entities
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName("ADMIN")
                .orElseThrow(() -> new RuntimeException("Role not found: ADMIN")));
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(roles);
        user.setName(request.getName());
        userRepository.save(user);

        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(token);
    }

    public AuthenticationResponse authenticate(User request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(token);
    }

    @Override
    public AuthenticationResponse login(LoginDTO loginDTO) {
        User user = userRepository.findByUsername(loginDTO.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Validate the user's password
        if (passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            String token = jwtService.generateToken(user);
            return new AuthenticationResponse(token);
        } else {
            throw new RuntimeException("Invalid password");
        }
    }
}
