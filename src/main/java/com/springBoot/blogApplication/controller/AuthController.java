package com.springBoot.blogApplication.controller;

import com.springBoot.blogApplication.entity.User;
import com.springBoot.blogApplication.payload.AuthenticationResponse;
import com.springBoot.blogApplication.payload.LoginDTO;
import com.springBoot.blogApplication.payload.RegisterDTO;
import com.springBoot.blogApplication.services.AuthService;
import com.springBoot.blogApplication.services.impl.AuthServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

private final AuthServiceImplementation authService;

    public AuthController(AuthServiceImplementation authService) {
        this.authService = authService;
    }


    //Build Login Rest API
    @PostMapping(value = {"/login" })
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(authService.login(loginDTO));
    }
    @PostMapping(value = {"/register" })
    public ResponseEntity<AuthenticationResponse> register(@RequestBody User registerDTO) {
        return ResponseEntity.ok(authService.register(registerDTO));
    }

}
